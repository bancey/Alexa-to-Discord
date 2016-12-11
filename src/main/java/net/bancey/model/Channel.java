package net.bancey.model;

/**
 * Created by Bancey on 11/12/2016.
 */
public class Channel {

    private String channelName;
    private String channelId;

    public Channel(String channelName, String channelId) {
        this.channelName = channelName;
        this.channelId = channelId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }
}
