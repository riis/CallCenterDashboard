package com.riis.pusher;

import java.util.Collections;
import java.util.Date;

import com.pusher.rest.Pusher;
import com.riis.model.AgentUpdateEvent;
import com.riis.model.CallCenterUpdateEvent;

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
        
        // push a test CallCEnterEvent
        CallCenterUpdateEvent callCenterEvent = new CallCenterUpdateEvent();
        callCenterEvent.setEventId("b0c3233d-b145-4ebc-adf5-3acc37ad1ac3");
        callCenterEvent.setSequenceNumber("4");
        callCenterEvent.setUserId("Admin_Joseph@voip.tnltd.net");
        callCenterEvent.setExternalApplicationId("CallCenterDashboard");
        callCenterEvent.setSubscriptionId("5d116b25-c5d6-4481-ac36-a32277d63cd4");
        callCenterEvent.setTargetId("roph0405@voip.tnltd.net");
        callCenterEvent.setAverageHandlingTime(0);
        callCenterEvent.setExpectedWaitTime(0);
        callCenterEvent.setAverageSpeedOfAnswer(0);
        callCenterEvent.setLongestWaitTime(0);
        callCenterEvent.setNumCallsInQueue(0);
        callCenterEvent.setNumAgentsAssigned(27);
        callCenterEvent.setNumAgentsStaffed(11);
        callCenterEvent.setNumStaffedAgentsIdle(0);
        callCenterEvent.setNumStaffedAgentsUnavailable(9);
        pusher.trigger("channel-two", "callCenterEvent", Collections.singletonMap("callCenterEvent", callCenterEvent.toPusherJSONFormat()));

        // Push a test Agent event
        AgentUpdateEvent agentUpdateEvent = new AgentUpdateEvent();
        agentUpdateEvent.setEventId("00fe5de6-a971-4215-986f-834f73623f05");
        agentUpdateEvent.setSequenceNumber("9");
        agentUpdateEvent.setUserId("Admin_Joseph@voip.tnltd.net");
        agentUpdateEvent.setExternalApplicationId("CallCenterDashboard");
        agentUpdateEvent.setSubscriptionId("03aa0953-b732-49c6-a634-bb6bf1ef5227");
        agentUpdateEvent.setTargetId("6234456521@voip.tnltd.net");
        agentUpdateEvent.setState("Available");
        agentUpdateEvent.setStateTimestamp(new Date(1412607268159L));
        agentUpdateEvent.setSignInTimestamp(new Date(1412607266974L));
        agentUpdateEvent.setTotalAvailableTime(0L);
        agentUpdateEvent.setAverageWrapUpTime(0L);
        pusher.trigger("channel-three", "agentEvent", Collections.singletonMap("agentEvent", agentUpdateEvent.toPusherJSONFormat()));
    }

    
    public void pushCallCenterEventNotification(CallCenterUpdateEvent event)
    {
        Pusher pusher = new Pusher(appId, apiKey, apiSecret);
        pusher.trigger("channel-two", "callCenterEvent", Collections.singletonMap("callCenterEvent", event.toPusherJSONFormat()));
    }

    
    public void pushAgentEventNotification(AgentUpdateEvent event)
    {
        Pusher pusher = new Pusher(appId, apiKey, apiSecret);
        pusher.trigger("channel-three", "agentEvent", Collections.singletonMap("agentEvent", event.toPusherJSONFormat()));
    }
}
