package com.riis.model;

import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.Before;
import org.junit.Test;

import com.riis.model.AgentUpdateEvent;

import static junit.framework.Assert.assertEquals;

public class AgentUpdateEventTest
{
    AgentUpdateEvent event;
    
    private static final String AGENT_SUBSCRIPTION_TERMINATION_XML_RESULT = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
            "<xsi:Event xsi1:type=\"xsi:SubscriptionEvent\" xmlns:xsi=\"http://schema.broadsoft.com/xsi\" xmlns:xsi1=\"http://www.w3.org/2001/XMLSchema-instance\">" +
                "<xsi:eventID>74113971-6bf3-49ad-8ffd-4bd3b209e4f7</xsi:eventID>" +
                "<xsi:sequenceNumber>18</xsi:sequenceNumber>" +
                "<xsi:userId>Admin_Joseph@voip.tnltd.net</xsi:userId>" +
                "<xsi:externalApplicationId>CallCenterDashboard</xsi:externalApplicationId>" +
                "<xsi:subscriptionId>26b8fe58-77e3-42b0-aa0a-7b0ac2ce91f0</xsi:subscriptionId>" +
                "<xsi:httpContact>" +
                    "<xsi:uri>ec2-54-205-41-129.compute-1.amazonaws.com:8080/CallCenterManager/webservices/agentSubscriptionCallback</xsi:uri>" +
                "</xsi:httpContact>" +
                "<xsi:targetId>6234456528@voip.tnltd.net</xsi:targetId>" +
                "<xsi:eventData xsi1:type=\"xsi:SubscriptionTerminatedEvent\"/>" +
            "</xsi:Event>";

    private static final String AGENT_SUBSCRIPTION_EVENT_XML_RESULT = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
    		"<xsi:Event xsi1:type=\"xsi:SubscriptionEvent\" xmlns:xsi=\"http://schema.broadsoft.com/xsi\" xmlns:xsi1=\"http://www.w3.org/2001/XMLSchema-instance\">" +
    		    "<xsi:eventID>00fe5de6-a971-4215-986f-834f73623f05</xsi:eventID>" +
    		    "<xsi:sequenceNumber>9</xsi:sequenceNumber>" +
    		    "<xsi:userId>Admin_Joseph@voip.tnltd.net</xsi:userId>" +
    		    "<xsi:externalApplicationId>CallCenterDashboard</xsi:externalApplicationId>" +
    		    "<xsi:subscriptionId>03aa0953-b732-49c6-a634-bb6bf1ef5227</xsi:subscriptionId>" +
    		    "<xsi:httpContact>" +
    		        "<xsi:uri>ec2-54-205-41-129.compute-1.amazonaws.com:8080/CallCenterManager/webservices/agentSubscriptionCallback</xsi:uri>" +
    		    "</xsi:httpContact>" +
    		    "<xsi:targetId>6234456521@voip.tnltd.net</xsi:targetId>" +
    		    "<xsi:eventData xsi1:type=\"xsi:AgentStateEvent\">" +
    		        "<xsi:agentStateInfo>" +
    		            "<xsi:state>Available</xsi:state>" +
    		            "<xsi:stateTimestamp>1412607268159</xsi:stateTimestamp>" +
    		            "<xsi:signInTimestamp>1412607266974</xsi:signInTimestamp>" +
    		            "<xsi:totalAvailableTime>0</xsi:totalAvailableTime>" +
    		            "<xsi:averageWrapUpTime>0</xsi:averageWrapUpTime>" +
    		            "</xsi:agentStateInfo>" +
    		    "</xsi:eventData>" +
    		"</xsi:Event>";

    @Before
    public void setup()
    {
        event = new AgentUpdateEvent();
    }


    @Test
    public void testReadEventFromXMLString() throws Exception
    {      
        event.readEventFromXMLString(AGENT_SUBSCRIPTION_EVENT_XML_RESULT);   
        
        assertEquals("00fe5de6-a971-4215-986f-834f73623f05",event.getEventId());
        assertEquals("9",event.getSequenceNumber());
        assertEquals("Admin_Joseph@voip.tnltd.net",event.getUserId());
        assertEquals("CallCenterDashboard",event.getExternalApplicationId());
        assertEquals("03aa0953-b732-49c6-a634-bb6bf1ef5227",event.getSubscriptionId());
        assertEquals("6234456521@voip.tnltd.net",event.getTargetId());
        assertEquals("Available", event.getState());
        assertEquals(1412607268159L, event.getStateTimestamp().getTime());
        assertEquals(1412607266974L, event.getSignInTimestamp().getTime());
        assertEquals((long)0L, (long)event.getTotalAvailableTime());
        assertEquals((long)0L, (long)event.getAverageWrapUpTime());
    }    


    @Test
    public void testReadSubscriptionTerminationEventFromXMLString() throws Exception
    {      
        event.readEventFromXMLString(AGENT_SUBSCRIPTION_TERMINATION_XML_RESULT);   
        
        assertEquals("74113971-6bf3-49ad-8ffd-4bd3b209e4f7",event.getEventId());
        assertEquals("18",event.getSequenceNumber());
        assertEquals("Admin_Joseph@voip.tnltd.net",event.getUserId());
        assertEquals("CallCenterDashboard",event.getExternalApplicationId());
        assertEquals("26b8fe58-77e3-42b0-aa0a-7b0ac2ce91f0",event.getSubscriptionId());
    }    
}
