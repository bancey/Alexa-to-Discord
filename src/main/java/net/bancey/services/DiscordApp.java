package net.bancey.services;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.*;
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
            jda = new JDABuilder(AccountType.BOT).setToken("MjU2NzgxMDg5ODI4MjQxNDA5.CzGnQA.TbkjBO73oUGd8_P4jjqsxoVJkI0").buildBlocking();
            jda.getPresence().setGame(Game.of("I am a non sentient being."));
        } catch (InterruptedException | RateLimitedException | LoginException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<TextChannel> getTextChannelsInGuild(String guildName) {
        if (jda != null) {
            ArrayList<TextChannel> channels = new ArrayList<>();
            for (Guild guild : jda.getGuildsByName(guildName, true)) {
                List<TextChannel> textChannels = guild.getTextChannels();
                channels.addAll(textChannels);
            }
            return channels;
        }
        return null;
    }

    public ArrayList<VoiceChannel> getVoiceChannelsInGuild(String guildName) {
        if(jda != null) {
            ArrayList<VoiceChannel> channels = new ArrayList<>();
            for(Guild guild : jda.getGuildsByName(guildName, true)) {
                List<VoiceChannel> voiceChannels = guild.getVoiceChannels();
                channels.addAll(voiceChannels);
            }
            return channels;
        }
        return null;
    }

    public ArrayList<Channel> getAllChannelsInGuild(String guildName) {
        ArrayList<Channel> allChannels = new ArrayList<>();
        ArrayList<VoiceChannel> voiceChannels = getVoiceChannelsInGuild(guildName);
        ArrayList<TextChannel> textChannels = getTextChannelsInGuild(guildName);
        allChannels.addAll(voiceChannels);
        allChannels.addAll(textChannels);
        return allChannels;
    }

    public ArrayList<Guild> getGuilds() {
        if (jda != null) {
            ArrayList<Guild> guilds = new ArrayList<>();
            guilds.addAll(jda.getGuilds());
            return guilds;
        }
        return null;
    }
}
