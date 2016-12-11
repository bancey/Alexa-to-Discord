package net.bancey;

import net.bancey.services.DiscordApp;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by Bancey on 11/12/2016.
 */
@SpringBootApplication
public class AlexaDiscordREST {

    private static DiscordApp discordInstance;

    public static void main(String[] args) {
        SpringApplication.run(AlexaDiscordREST.class, args);
        if(args.length > 0) {
            DiscordApp app = new DiscordApp(args[0]);
            AlexaDiscordREST.discordInstance = app;
        }
    }

    public static DiscordApp getDiscordInstance() {
        return discordInstance;
    }
}
