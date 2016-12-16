package net.bancey;

import com.amazon.speech.speechlet.Speechlet;
import com.amazon.speech.speechlet.servlet.SpeechletServlet;
import net.bancey.services.DiscordApp;
import net.bancey.speechlets.DiscordSpeechlet;
import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthAccessTokenResponse;
import org.apache.oltu.oauth2.client.response.OAuthAuthzResponse;
import org.apache.oltu.oauth2.common.OAuthProviderType;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.apache.oltu.oauth2.common.message.types.ResponseType;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * Created by Bancey on 11/12/2016.
 */
@Controller
@SpringBootApplication
@EnableAutoConfiguration
public class AlexaToDiscord extends SpringBootServletInitializer {

    private final String BASE_URL = "https://discordapp.com/api";
    private final String AUTH_URL = BASE_URL + "/oauth/authorize";
    private final String TOKEN_URL = BASE_URL + "/oauth/token";
    private final String CALLBACK_URL = "https://alexa-discord.herokuapp.com/oauth/callback";
    private final String CLIENT_SECRET = "sGM8PzikQ5oqcrxm8cvxh2PSfWFvSxJa";

    //Declare class fields
    private static DiscordApp discordInstance;

    /*
     * Constructor for the AlexaToDiscord class, this is the entry point of the application
     */
    public static void main(String[] args) {
        SpringApplication.run(AlexaToDiscord.class, args);
        String token = System.getenv("DISCORD_TOKEN");
        AlexaToDiscord.discordInstance = new DiscordApp(token);
    }

    /*
     * Method that maps the url / to the string "Alexa-Discord is running!" this allows you to see if the
     * application is running by navigating to the application in your browser
     */
    @RequestMapping("/")
    @ResponseBody
    public String home() {
        return "Alexa-Discord is running!";
    }

    @RequestMapping("/oauth/authorize")
    @ResponseBody
    public ModelAndView authorize(@RequestParam("clientId") String clientId, @RequestParam("state") String state, @RequestParam("redirectURI") String redirectURI, HttpServletRequest req, HttpServletResponse res) {
        try {
            res.addCookie(new Cookie("clientId", clientId));
            res.addCookie(new Cookie("state", state));
            res.addCookie(new Cookie("redirectURI", redirectURI));

            OAuthClientRequest request = OAuthClientRequest
                    .authorizationLocation(AUTH_URL)
                    .setClientId(clientId)
                    .setRedirectURI(CALLBACK_URL)
                    .setResponseType(ResponseType.CODE.toString())
                    .setScope("guild")
                    .buildQueryMessage();

            return new ModelAndView(new RedirectView(request.getLocationUri()));
        } catch (OAuthSystemException e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("/oauth/callback")
    @ResponseBody
    public ModelAndView callback(HttpServletRequest request, HttpServletResponse response) {
        try {
            String clientId = findCookieValue(request, "clientId");
            String state = findCookieValue(request, "state");
            String redirectURI = findCookieValue(request, "redirectURL");

            OAuthAuthzResponse oar;
            oar = OAuthAuthzResponse.oauthCodeAuthzResponse(request);
            String code = oar.getCode();

            OAuthClientRequest oAuthClientRequest = OAuthClientRequest
                    .tokenLocation(TOKEN_URL)
                    .setGrantType(GrantType.AUTHORIZATION_CODE)
                    .setClientId(clientId)
                    .setClientSecret(CLIENT_SECRET)
                    .setRedirectURI(CALLBACK_URL)
                    .setCode(code)
                    .buildQueryMessage();

            OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());

            OAuthAccessTokenResponse tokenResponse = oAuthClient.accessToken(oAuthClientRequest);
            String accessToken = tokenResponse.getAccessToken();
            String tokenType = tokenResponse.getTokenType();
            Long expiresIn = tokenResponse.getExpiresIn();
            System.out.println(accessToken + ":" + tokenType + ":" + expiresIn);
            redirectURI += "#state=" + state + "&access_token=" + accessToken + "&token_type=" + tokenType;
            return new ModelAndView(new RedirectView(redirectURI));
        } catch (OAuthProblemException e) {
            e.printStackTrace();
        } catch (OAuthSystemException e) {
            e.printStackTrace();
        }
        return new ModelAndView(new RedirectView("/"));
    }


    /*
     * Method that configures the spring application
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(AlexaToDiscord.class);
    }

    /*
     * Method that allows us to map our Speechlet that handles the Alexa requests to the url /alexa
     */
    @Bean
    public ServletRegistrationBean servletRegistrationBean(){
        return new ServletRegistrationBean(createServlet(new DiscordSpeechlet()),"/alexa");
    }

    /*
     * Method that wraps a Speechlet in a SpeechletServlet which we can publish using spring
     */
    private SpeechletServlet createServlet(final Speechlet speechlet) {
        SpeechletServlet servlet = new SpeechletServlet();
        servlet.setSpeechlet(speechlet);
        return servlet;
    }

    /*
     * Method that returns the instance of the DiscordApp class
     */
    public static DiscordApp getDiscordInstance() {
        return discordInstance;
    }

    private String findCookieValue(HttpServletRequest request, String key) {
        Cookie[] cookies = request.getCookies();

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(key)) {
                return cookie.getValue();
            }
        }
        return "";
    }
}
