package net.bancey.services;

import net.bancey.model.Channel;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bancey on 11/12/2016.
 */
public class DiscordApp {

    private JDA jda = null;

    public DiscordApp(String token) {
        try {
            jda = new JDABuilder(AccountType.BOT).setToken(token).buildBlocking();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (RateLimitedException e) {
            e.printStackTrace();
        } catch (LoginException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Channel> getTextChannelsInGuild(String guildName) {
        if (jda != null) {
            ArrayList<Channel> channels = new ArrayList<>();
            for (Guild guild : jda.getGuildsByName(guildName, true)) {
                List<TextChannel> textChannels = guild.getTextChannels();
                for(TextChannel ch: textChannels) {
                    channels.add(new Channel(ch.getName(), ch.getId()));
                }
            }
            return channels;
        }
        return null;
    }
}
