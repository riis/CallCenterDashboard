package com.riis.model;

import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.Before;
import org.junit.Test;

import com.riis.model.Event;

import static junit.framework.Assert.assertEquals;

public class EventTest
{
    Event event;
    
    private static final String AGENT_SUBSCRIPTION_XML_RESULT = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
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
            "<xsi:eventData xsi1:type=\"xsi:SubscriptionTerminatedEvent\"/></xsi:Event>";


    @Before
    public void setup()
    {
        event = new Event();
    }


@Test
    public void testReadEventFromXMLString() throws Exception
    {      
        event.readAgentEventFromXMLString(AGENT_SUBSCRIPTION_XML_RESULT);   
        
        assertEquals("74113971-6bf3-49ad-8ffd-4bd3b209e4f7",event.getEventId());
        assertEquals("18",event.getSequenceNumber());
        assertEquals("Admin_Joseph@voip.tnltd.net",event.getUserId());
        assertEquals("CallCenterDashboard",event.getExternalApplicationId());
        assertEquals("26b8fe58-77e3-42b0-aa0a-7b0ac2ce91f0",event.getSubscriptionId());
    }    
}
