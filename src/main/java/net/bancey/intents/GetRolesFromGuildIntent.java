package net.bancey.intents;

import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.Reprompt;
import com.amazon.speech.ui.SimpleCard;
import net.bancey.AlexaToDiscord;
import net.bancey.services.DiscordApp;
import net.dv8tion.jda.core.entities.Role;

import java.util.ArrayList;

/**
 *
 * Created by abance on 15/12/2016.
 */
public class GetRolesFromGuildIntent extends AlexaDiscordIntent {

    public GetRolesFromGuildIntent(String name) {
        super(name);
    }

    @Override
    public SpeechletResponse handle(String guild) {
        if(guild != null) {
            DiscordApp discordApp = AlexaToDiscord.getDiscordInstance();
            ArrayList<Role> roles = discordApp.getRolesFromGuild(guild);

            String speechText;
            if(roles.size() > 0) {
                speechText = "I found " + roles.size() + " roles in that guild. THey are: ";
                for(int i = 0; i<roles.size(); i++) {
                    if(i != (roles.size() - 1)) {
                        speechText += roles.get(i).getName() + ", ";
                    } else {
                        speechText += "and " + roles.get(i).getName() + ".";
                    }
                }
            } else {
                speechText = "Sorry I didn't find any roles on that server!";
            }

            SimpleCard card = new SimpleCard();
            card.setTitle("Roles found!");
            card.setContent(speechText);

            PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
            speech.setText(speechText);

            return SpeechletResponse.newTellResponse(speech, card);
        }
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText("Please select a guild first!");
        Reprompt reprompt = new Reprompt();
        reprompt.setOutputSpeech(speech);
        return SpeechletResponse.newAskResponse(speech, reprompt);
    }
}
