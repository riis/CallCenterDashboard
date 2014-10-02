package com.riis.pusher;

import java.util.Collections;

import com.pusher.rest.Pusher;
import com.riis.model.Event;

public class PusherGateway
{
    private String appId = "91457";
    private String apiKey = "da81ce797d453db253d1";
    private String apiSecret = "1b3278510bc037b2db85";

    
    
    public String getAppId()
    {
        return appId;
    }
    
    
    public void setAppId(String appId)
    {
        this.appId = appId;
    }
    
    
    public String getApiKey()
    {
        return apiKey;
    }
    
    
    public void setApiKey(String apiKey)
    {
        this.apiKey = apiKey;
    }
    
    
    public String getApiSecret()
    {
        return apiSecret;
    }

    
    public void setApiSecret(String apiSecret)
    {
        this.apiSecret = apiSecret;
    }
    
    public void pushTestNotification()
    {
        Pusher pusher = new Pusher(appId, apiKey, apiSecret);
        pusher.trigger("channel-one", "test_event", Collections.singletonMap("pushTestNotification", "hello test world"));
    }

    public void pushEventNotification(Event event)
    {
        Pusher pusher = new Pusher(appId, apiKey, apiSecret);
        pusher.trigger("channel-two", "event", Collections.singletonMap("event", event.toString()));
    }
}
