package net.bancey;

import com.amazon.speech.speechlet.Speechlet;
import com.amazon.speech.speechlet.servlet.SpeechletServlet;
import net.bancey.services.DiscordApp;
import net.bancey.speechlets.DiscordSpeechlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * Created by Bancey on 11/12/2016.
 */
@Controller
@SpringBootApplication
@EnableAutoConfiguration
public class AlexaToDiscord extends SpringBootServletInitializer {

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
     * Method that returns the instance of the DiscordApp class
     */
    public static DiscordApp getDiscordInstance() {
        return discordInstance;
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
    public ServletRegistrationBean servletRegistrationBean() {
        return new ServletRegistrationBean(createServlet(new DiscordSpeechlet()), "/alexa");
    }

    /*
     * Method that wraps a Speechlet in a SpeechletServlet which we can publish using spring
     */
    private SpeechletServlet createServlet(final Speechlet speechlet) {
        SpeechletServlet servlet = new SpeechletServlet();
        servlet.setSpeechlet(speechlet);
        return servlet;
    }

}
