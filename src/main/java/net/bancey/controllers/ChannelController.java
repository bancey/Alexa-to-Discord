package net.bancey.controllers;

import net.bancey.AlexaDiscordREST;
import net.bancey.model.Channel;
import net.bancey.services.DiscordApp;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

/**
 * Created by Bancey on 11/12/2016.
 */
@RestController
public class ChannelController {

    @RequestMapping("/channel/{channelName}")
    public ArrayList<Channel> getChannel(@PathVariable("channelName") String channelName, @RequestParam("guild") String guildName) {
        System.out.println(channelName + ":" + guildName);
        DiscordApp app = AlexaDiscordREST.getDiscordInstance();
        ArrayList<Channel> channels = app.getTextChannelsInGuild(guildName);
        return channels;
    }
}
