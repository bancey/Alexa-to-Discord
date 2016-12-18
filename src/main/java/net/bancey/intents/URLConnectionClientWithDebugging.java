package net.bancey.intents;

import org.apache.oltu.oauth2.client.HttpClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthClientResponse;
import org.apache.oltu.oauth2.client.response.OAuthClientResponseFactory;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.utils.OAuthUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

/**
 * Alexa-to-Discord
 * Created by abance on 18/12/2016.
 */
public class URLConnectionClientWithDebugging implements HttpClient {

    public URLConnectionClientWithDebugging() {
    }

    public <T extends OAuthClientResponse> T execute(OAuthClientRequest request, Map<String, String> headers,
                                                     String requestMethod, Class<T> responseClass)
            throws OAuthSystemException, OAuthProblemException {

        InputStream responseBody = null;
        URLConnection c;
        Map<String, List<String>> responseHeaders = new HashMap<String, List<String>>();
        int responseCode;
        try {
            URL url = new URL(request.getLocationUri());

            c = url.openConnection();
            responseCode = -1;
            if (c instanceof HttpURLConnection) {
                HttpURLConnection httpURLConnection = (HttpURLConnection) c;

                if (headers != null && !headers.isEmpty()) {
                    for (Map.Entry<String, String> header : headers.entrySet()) {
                        System.out.println("Header: " + header.getKey() + ":" + header.getValue());
                        httpURLConnection.addRequestProperty(header.getKey(), header.getValue());
                    }
                }

                if (request.getHeaders() != null) {
                    for (Map.Entry<String, String> header : request.getHeaders().entrySet()) {
                        System.out.println("Header: " + header.getKey() + ":" + header.getValue());
                        httpURLConnection.addRequestProperty(header.getKey(), header.getValue());
                    }
                }

                if (OAuthUtils.isEmpty(requestMethod)) {
                    System.out.println("Using GET");
                    httpURLConnection.setRequestMethod(OAuth.HttpMethod.GET);
                } else {
                    System.out.println("Using " + requestMethod);
                    httpURLConnection.setRequestMethod(requestMethod);
                    setRequestBody(request, requestMethod, httpURLConnection);
                }

                httpURLConnection.connect();

                InputStream inputStream;
                responseCode = httpURLConnection.getResponseCode();
                if (responseCode == SC_BAD_REQUEST || responseCode == SC_UNAUTHORIZED) {
                    inputStream = httpURLConnection.getErrorStream();
                } else {
                    inputStream = httpURLConnection.getInputStream();
                }

                responseHeaders = httpURLConnection.getHeaderFields();
                responseBody = inputStream;
            }
        } catch (IOException e) {
            throw new OAuthSystemException(e);
        }

        return OAuthClientResponseFactory
                .createCustomResponse(responseBody, c.getContentType(), responseCode, responseHeaders, responseClass);
    }

    private void setRequestBody(OAuthClientRequest request, String requestMethod, HttpURLConnection httpURLConnection)
            throws IOException {
        String requestBody = request.getBody();
        if (OAuthUtils.isEmpty(requestBody)) {
            return;
        }

        if (OAuth.HttpMethod.POST.equals(requestMethod) || OAuth.HttpMethod.PUT.equals(requestMethod)) {
            httpURLConnection.setDoOutput(true);
            OutputStream ost = httpURLConnection.getOutputStream();
            PrintWriter pw = new PrintWriter(ost);
            pw.print(requestBody);
            pw.flush();
            pw.close();
        }
    }

    @Override
    public void shutdown() {
        // Nothing to do here
    }

}
