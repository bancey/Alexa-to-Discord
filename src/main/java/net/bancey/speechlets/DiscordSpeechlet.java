package net.bancey.speechlets;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.slu.Slot;
import com.amazon.speech.speechlet.*;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.Reprompt;
import com.amazon.speech.ui.SimpleCard;
import net.bancey.intents.*;

import java.util.Map;

/**
 *
 * Created by abance on 13/12/2016.
 */
public class DiscordSpeechlet implements Speechlet {

    private String selectedGuild;
    private AlexaDiscordIntent[] intents = {new GetAllChannelsFromGuildIntent("GetAllChannelsFromGuildIntent"), new GetTextChannelsFromGuildIntent("GetTextChannelsFromGuildIntent"), new GetVoiceChannelsFromGuildIntent("GetVoiceChannelsFromGuildIntent"),new GetGuildsIntent("GetGuildsIntent"), new SelectGuildIntent("SelectGuildIntent"), new GetRolesFromGuildIntent("GetRolesFromGuildIntent")};
    private static final String GUILD_KEY = "Guild";

    @Override
    public void onSessionStarted(SessionStartedRequest sessionStartedRequest, Session session) throws SpeechletException {
        //Startup login
    }

    @Override
    public SpeechletResponse onLaunch(LaunchRequest launchRequest, Session session) throws SpeechletException {
        return onLaunchResponse();
    }

    @Override
    public SpeechletResponse onIntent(IntentRequest intentRequest, Session session) throws SpeechletException {
        Intent intent = intentRequest.getIntent();
        if("AMAZON.StopIntent".equals(intent.getName())) {
            return onExitResponse();
        } else if("AMAZON.CancelIntent".equals(intent.getName())) {
            return onExitResponse();
        } else if("AMAZON.HelpIntent".equals(intent.getName())) {
            return onLaunchResponse();
        } else {
            for(AlexaDiscordIntent alexaDiscordIntent: intents) {
                if(alexaDiscordIntent.getName().equals(intent.getName())) {
                    if(alexaDiscordIntent.getName().equals("SelectGuildIntent")) {
                        SelectGuildIntent guildIntent = (SelectGuildIntent) alexaDiscordIntent;
                        Map<String, Slot> slots = intent.getSlots();
                        Slot guildSlot = slots.get(GUILD_KEY);
                        SpeechletResponse response = guildIntent.handle(guildSlot.getValue());
                        selectedGuild = guildIntent.getSelectedGuild();
                        return response;
                    }
                    if(alexaDiscordIntent.getName().equals("GetTextChannelsFromGuildIntent") || alexaDiscordIntent.getName().equals("GetVoiceChannelsFromGuildIntent") || alexaDiscordIntent.getName().equals("GetAllChannelsFromGuildIntent") || alexaDiscordIntent.getName().equals("GetRolesFromGuildIntent")) {
                        if(selectedGuild != null) {
                            return alexaDiscordIntent.handle(selectedGuild);
                        }
                        return alexaDiscordIntent.handle(null);
                    }
                    return alexaDiscordIntent.handle("s u c c m y f u c c");
                }
            }
        }
        return onErrorResponse();
    }

    @Override
    public void onSessionEnded(SessionEndedRequest sessionEndedRequest, Session session) throws SpeechletException {
        //Shutdown login
    }

    private SpeechletResponse onLaunchResponse() {
        String speechText = "Welcome to the Alexa Discord Skill, you can say get text channels, get voice channels, get all channels, get guilds or select guild";
        String repromptText = "What would you like to do?";

        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(speechText);

        PlainTextOutputSpeech repromptSpeech = new PlainTextOutputSpeech();
        repromptSpeech.setText(repromptText);

        Reprompt reprompt = new Reprompt();
        reprompt.setOutputSpeech(repromptSpeech);

        return SpeechletResponse.newAskResponse(speech, reprompt);
    }

    private SpeechletResponse onErrorResponse() {
        String speechText = "I encountered an error while processing your request.";
        String repromptText = "Please try again.";

        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(speechText);

        PlainTextOutputSpeech repromptSpeech = new PlainTextOutputSpeech();
        repromptSpeech.setText(repromptText);

        Reprompt reprompt = new Reprompt();
        reprompt.setOutputSpeech(repromptSpeech);

        return SpeechletResponse.newAskResponse(speech, reprompt);
    }

    private SpeechletResponse onExitResponse() {
        String speechText = "Goodbye!";

        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(speechText);

        return SpeechletResponse.newTellResponse(speech);
    }
}
