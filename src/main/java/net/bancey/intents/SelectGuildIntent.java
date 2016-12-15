package net.bancey.intents;

import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.Reprompt;
import com.amazon.speech.ui.SimpleCard;
import net.bancey.AlexaToDiscord;
import net.bancey.services.DiscordApp;
import net.dv8tion.jda.core.entities.Guild;

import java.util.ArrayList;

/**
 *
 * Created by Bancey on 13/12/2016.
 */
public class SelectGuildIntent extends AlexaDiscordIntent {

    private String selectedGuild;

    public SelectGuildIntent(String name) {
        super(name);
    }

    @Override
    public SpeechletResponse handle(String guild) {
        DiscordApp discordApp = AlexaToDiscord.getDiscordInstance();
        ArrayList<Guild> guilds = discordApp.getGuilds();

        String speechText, repromptText;
        if (guild != null && !(Integer.parseInt(guild) > (guilds.size() - 1)) && !(Integer.parseInt(guild) < 0)) {
            int guildNumber = Integer.parseInt(guild);
            speechText = "The guild you have selected is " + guilds.get(guildNumber).getName() + ".";
            repromptText = "What would you like to do now?";
            selectedGuild = guilds.get(guildNumber).getId();
        } else {
            speechText = "Sorry I didn't pick up a guild number or the number you said wasn't accepted. If you are unsure of the number please say get guilds first.";
            repromptText = "Please try again.";
        }

        SimpleCard card = new SimpleCard();
        card.setTitle("Channels found!");
        card.setContent(speechText);

        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(speechText);

        PlainTextOutputSpeech repromptSpeech = new PlainTextOutputSpeech();
        repromptSpeech.setText(repromptText);

        Reprompt reprompt = new Reprompt();
        reprompt.setOutputSpeech(repromptSpeech);

        return SpeechletResponse.newAskResponse(speech, reprompt, card);
    }

    public String getSelectedGuild() {
        return selectedGuild;
    }
}
