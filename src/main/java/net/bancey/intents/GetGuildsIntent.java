package net.bancey.intents;

import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.SimpleCard;
import net.bancey.AlexaDiscordREST;
import net.bancey.services.DiscordApp;
import net.dv8tion.jda.core.entities.Guild;

import java.util.ArrayList;

/**
 * Created by Bancey on 13/12/2016.
 */
public class GetGuildsIntent extends AlexaDiscordIntent{

    public GetGuildsIntent(String name) {
        super(name);
    }

    @Override
    public SpeechletResponse handle(String guild) {
        DiscordApp discordApp = AlexaDiscordREST.getDiscordInstance();
        ArrayList<Guild> guilds = discordApp.getGuilds();

        String speechText;
        if (guilds.size() > 0 && guilds.size() > 1) {
            speechText = "There are " + guilds.size() + " that I am connected to. They are ";
            for (int i = 0; i < guilds.size(); i++) {
                if (i != (guilds.size() - 1)) {
                    speechText += guilds.get(i).getName() + ", ";
                } else if (guilds.size() != 1) {
                    speechText += "and " + guilds.get(i).getName() + ".";
                } else {
                    speechText += guilds.get(i).getName() + ".";
                }
            }
        } else if(guilds.size() > 0 && guilds.size() < 2) {
            speechText = "There is " + guilds.size() + " that I am connected to. It is ";
            for(int i = 0; i < guilds.size(); i++) {
                speechText += guilds.get(i).getName() + ".";
            }
        } else {
            speechText = "I couldn't find any guilds!";
        }

        SimpleCard card = new SimpleCard();
        card.setTitle("Guilds!");
        card.setContent(speechText);

        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(speechText);

        return SpeechletResponse.newTellResponse(speech, card);
    }
}
