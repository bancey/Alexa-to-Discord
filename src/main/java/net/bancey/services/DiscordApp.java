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
 *
 * Created by Bancey on 11/12/2016.
 */
public class DiscordApp {

    private JDA jda = null;

    public DiscordApp(String token) {
        try {
            jda = new JDABuilder(AccountType.BOT).setToken(token).buildBlocking();
            jda.getPresence().setGame(Game.of("I am a non sentient being."));
        } catch (InterruptedException | RateLimitedException | LoginException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<TextChannel> getTextChannelsInGuild(String guildId) {
        if (jda != null) {
            ArrayList<TextChannel> channels = new ArrayList<>();
            Guild guild = jda.getGuildById(guildId);
            channels.addAll(guild.getTextChannels());
            return channels;
        }
        return null;
    }

    public ArrayList<VoiceChannel> getVoiceChannelsInGuild(String guildId) {
        if(jda != null) {
            ArrayList<VoiceChannel> channels = new ArrayList<>();
            Guild guild = jda.getGuildById(guildId);
            channels.addAll(guild.getVoiceChannels());
            return channels;
        }
        return null;
    }

    public ArrayList<Channel> getAllChannelsInGuild(String guildId) {
        ArrayList<Channel> allChannels = new ArrayList<>();
        ArrayList<VoiceChannel> voiceChannels = getVoiceChannelsInGuild(guildId);
        ArrayList<TextChannel> textChannels = getTextChannelsInGuild(guildId);
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

    public Guild getGuildById(String guildId) {
        if(jda != null) {
            return jda.getGuildById(guildId);
        }
        return null;
    }

    public ArrayList<Role> getRolesFromGuild(String guildId) {
        if(jda != null) {
            ArrayList<Role> roles = new ArrayList<>();
            roles.addAll(jda.getGuildById(guildId).getRoles());
            return roles;
        }
        return null;
    }
}
