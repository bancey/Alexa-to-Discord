package net.bancey.intents;

import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.Reprompt;
import com.amazon.speech.ui.SimpleCard;
import net.bancey.AlexaDiscordREST;
import net.bancey.services.DiscordApp;
import net.dv8tion.jda.core.entities.TextChannel;

import java.util.ArrayList;

/**
 * Created by Bancey on 13/12/2016.
 */
public class SelectGuildIntent extends AlexaDiscordIntent {

    private String selectedGuild;

    public SelectGuildIntent(String name) {
        super(name);
    }

    @Override
    public SpeechletResponse handle(String guild) {
        String speechText, repromptText;
        if (guild != null) {
            speechText = "The guild you have selected is " + guild + ".";
            repromptText = "What would you like to do now?";
            selectedGuild = guild;
        } else {
            speechText = "Sorry I didn't pick up a guild name.";
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
