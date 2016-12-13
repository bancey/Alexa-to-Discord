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

/**
 * Created by Bancey on 11/12/2016.
 */
@SpringBootApplication
@EnableAutoConfiguration
public class AlexaDiscordREST extends SpringBootServletInitializer {

    private static DiscordApp discordInstance;

    public static void main(String[] args) {
        SpringApplication.run(AlexaDiscordREST.class, args);
        //if(args.length > 0) {
            DiscordApp app = new DiscordApp("");
            AlexaDiscordREST.discordInstance = app;
        //}
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(AlexaDiscordREST.class);
    }

    @Bean
    public ServletRegistrationBean servletRegistrationBean(){
        return new ServletRegistrationBean(createServlet(new DiscordSpeechlet()),"/alexa");
    }

    public static SpeechletServlet createServlet(final Speechlet speechlet) {
        SpeechletServlet servlet = new SpeechletServlet();
        servlet.setSpeechlet(speechlet);
        return servlet;
    }

    public static DiscordApp getDiscordInstance() {
        return discordInstance;
    }
}
