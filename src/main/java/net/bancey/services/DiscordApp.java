package net.bancey.services;

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
            jda = new JDABuilder(AccountType.BOT).setToken("MjU2NzgxMDg5ODI4MjQxNDA5.CzGnQA.TbkjBO73oUGd8_P4jjqsxoVJkI0").buildBlocking();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (RateLimitedException e) {
            e.printStackTrace();
        } catch (LoginException e) {
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
}
