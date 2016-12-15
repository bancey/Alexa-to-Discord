package net.bancey.intents;

import com.amazon.speech.speechlet.SpeechletResponse;

/**
 *
 * Created by abance on 13/12/2016.
 */
public abstract class AlexaDiscordIntent {

    private String name;

    AlexaDiscordIntent(String name) {
        this.name = name;
    }

    public abstract SpeechletResponse handle(String guild);

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
