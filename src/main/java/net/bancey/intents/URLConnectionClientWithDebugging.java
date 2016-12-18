package net.bancey.intents;

import org.apache.oltu.oauth2.client.HttpClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthClientResponse;
import org.apache.oltu.oauth2.client.response.OAuthClientResponseFactory;
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
import java.util.Map;

/**
 * Alexa-to-Discord
 * Created by abance on 18/12/2016.
 */
public class URLConnectionClientWithDebugging implements HttpClient {

    public URLConnectionClientWithDebugging() {
    }

    public <T extends OAuthClientResponse> T execute(OAuthClientRequest request, Map<String, String> headers, String requestMethod, Class<T> responseClass) throws OAuthSystemException, OAuthProblemException {
        InputStream responseBody = null;
        Object responseHeaders = new HashMap();

        URLConnection c;
        int responseCode;
        try {
            URL e = new URL(request.getLocationUri());
            c = e.openConnection();
            responseCode = -1;
            if(c instanceof HttpURLConnection) {
                HttpURLConnection httpURLConnection = (HttpURLConnection)c;
                Iterator inputStream;
                Map.Entry header;
                if(headers != null && !headers.isEmpty()) {
                    inputStream = headers.entrySet().iterator();

                    while(inputStream.hasNext()) {
                        header = (Map.Entry)inputStream.next();
                        httpURLConnection.addRequestProperty((String)header.getKey(), (String)header.getValue());
                    }
                }

                if(request.getHeaders() != null) {
                    System.out.println("Headers are not null!");
                    inputStream = request.getHeaders().entrySet().iterator();

                    while(inputStream.hasNext()) {
                        header = (Map.Entry)inputStream.next();
                        System.out.println(header);
                        httpURLConnection.addRequestProperty((String)header.getKey(), (String)header.getValue());
                    }
                }

                if(OAuthUtils.isEmpty(requestMethod)) {
                    System.out.println("Using GET");
                    httpURLConnection.setRequestMethod("GET");
                } else {
                    System.out.println("Using " + requestMethod);
                    httpURLConnection.setRequestMethod(requestMethod);
                    this.setRequestBody(request, requestMethod, httpURLConnection);
                }

                httpURLConnection.connect();
                responseCode = httpURLConnection.getResponseCode();
                InputStream inputStream1;
                if(responseCode != 400 && responseCode != 401) {
                    inputStream1 = httpURLConnection.getInputStream();
                } else {
                    inputStream1 = httpURLConnection.getErrorStream();
                }

                responseHeaders = httpURLConnection.getHeaderFields();
                responseBody = inputStream1;
            }
        } catch (IOException var13) {
            throw new OAuthSystemException(var13);
        }

        return OAuthClientResponseFactory.createCustomResponse(responseBody, c.getContentType(), responseCode, (Map)responseHeaders, responseClass);
    }

    private void setRequestBody(OAuthClientRequest request, String requestMethod, HttpURLConnection httpURLConnection) throws IOException {
        String requestBody = request.getBody();
        if(!OAuthUtils.isEmpty(requestBody)) {
            if("POST".equals(requestMethod) || "PUT".equals(requestMethod)) {
                httpURLConnection.setDoOutput(true);
                OutputStream ost = httpURLConnection.getOutputStream();
                PrintWriter pw = new PrintWriter(ost);
                pw.print(requestBody);
                pw.flush();
                pw.close();
            }

        }
    }

    public void shutdown() {
    }
}
