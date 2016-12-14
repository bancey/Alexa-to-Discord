package net.bancey.intents;

import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.Reprompt;
import com.amazon.speech.ui.SimpleCard;
import net.bancey.AlexaDiscordREST;
import net.bancey.services.DiscordApp;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.VoiceChannel;

import java.util.ArrayList;

/**
 * Created by abance on 14/12/2016.
 */
public class GetVoiceChannelsFromGuildIntent extends AlexaDiscordIntent {

    public GetVoiceChannelsFromGuildIntent(String name) {
        super(name);
    }

    @Override
    public SpeechletResponse handle(String guild) {
        if(guild != null) {
            DiscordApp discordApp = AlexaDiscordREST.getDiscordInstance();
            ArrayList<VoiceChannel> channels = discordApp.getVoiceChannelsInGuild(guild);

            String speechText;
            if (channels.size() > 0) {
                speechText = "I found " + channels.size() + " voice channels in " + guild + ". They are ";
                for (int i = 0; i < channels.size(); i++) {
                    if (i != (channels.size() - 1)) {
                        speechText += channels.get(i).getName() + ", ";
                    } else {
                        speechText += "and " + channels.get(i).getName() + ".";
                    }
                }
            } else {
                speechText = "I couldn't find any text channels in that guild!";
            }

            SimpleCard card = new SimpleCard();
            card.setTitle("Channels found!");
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
