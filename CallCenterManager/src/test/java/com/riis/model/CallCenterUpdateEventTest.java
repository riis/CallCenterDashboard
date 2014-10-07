package com.riis.broadsoft.model;

import static junit.framework.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.riis.model.AgentUpdateEvent;
import com.riis.model.CallCenterUpdateEvent;

public class CallCenterUpdateEventTest
{
    CallCenterUpdateEvent event;
    
    private static final String CALL_CENTER_SUBSCRIPTION_TERMINATION_XML_RESULT = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
            "<xsi:Event xsi1:type=\"xsi:SubscriptionEvent\" xmlns:xsi=\"http://schema.broadsoft.com/xsi\" xmlns:xsi1=\"http://www.w3.org/2001/XMLSchema-instance\">" +
                "<xsi:eventID>12d28bed-ae54-41a9-b76c-6fa021a926f8</xsi:eventID>" +
                "<xsi:sequenceNumber>2</xsi:sequenceNumber>" +
                "<xsi:userId>gnolanAdmin1@xdp.broadsoft.com</xsi:userId>" +
                "<xsi:externalApplicationId>CallCenterDashboard</xsi:externalApplicationId>" +
                "<xsi:subscriptionId>1050a793-010c-49be-9392-4cfb1b6d8afa</xsi:subscriptionId>" +
                "<xsi:httpContact>" +
                    "<xsi:uri>ec2-54-205-41-129.compute-1.amazonaws.com:8080/CallCenterManager/webservices/callCenterSubscriptionCallback</xsi:uri>" +
                "</xsi:httpContact>" +
                "<xsi:targetId>callCenter3@xdp.broadsoft.com</xsi:targetId>" +
                "<xsi:eventData xsi1:type=\"xsi:SubscriptionTerminatedEvent\"/>" +
            "</xsi:Event>";

    private static final String CALL_CENTER_SUBSCRIPTION_EVENT_XML_RESULT = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
            "<xsi:Event xsi1:type=\"xsi:SubscriptionEvent\" xmlns:xsi=\"http://schema.broadsoft.com/xsi\" xmlns:xsi1=\"http://www.w3.org/2001/XMLSchema-instance\">" +
                "<xsi:eventID>b0c3233d-b145-4ebc-adf5-3acc37ad1ac3</xsi:eventID>" +
                "<xsi:sequenceNumber>4</xsi:sequenceNumber>" +
                "<xsi:userId>Admin_Joseph@voip.tnltd.net</xsi:userId>" +
                "<xsi:externalApplicationId>CallCenterDashboard</xsi:externalApplicationId>" +
                "<xsi:subscriptionId>5d116b25-c5d6-4481-ac36-a32277d63cd4</xsi:subscriptionId>" +
                "<xsi:httpContact>" +
                    "<xsi:uri>ec2-54-205-41-129.compute-1.amazonaws.com:8080/CallCenterManager/webservices/callCenterSubscriptionCallback</xsi:uri>" +
                "</xsi:httpContact>" +
                "<xsi:targetId>roph0405@voip.tnltd.net</xsi:targetId>" +
                "<xsi:eventData xsi1:type=\"xsi:CallCenterMonitoringEvent\">" +
                    "<xsi:monitoringStatus>" +
                        "<xsi:averageHandlingTime>0</xsi:averageHandlingTime>" +
                        "<xsi:expectedWaitTime>0</xsi:expectedWaitTime>" +
                        "<xsi:averageSpeedOfAnswer>0</xsi:averageSpeedOfAnswer>" +
                        "<xsi:longestWaitTime>0</xsi:longestWaitTime>" +
                        "<xsi:numCallsInQueue>0</xsi:numCallsInQueue>" +
                        "<xsi:numAgentsAssigned>27</xsi:numAgentsAssigned>" +
                        "<xsi:numAgentsStaffed>11</xsi:numAgentsStaffed>" +
                        "<xsi:numStaffedAgentsIdle>0</xsi:numStaffedAgentsIdle>" +
                        "<xsi:numStaffedAgentsUnavailable>9</xsi:numStaffedAgentsUnavailable>" +
                    "</xsi:monitoringStatus>" +
                "</xsi:eventData>" +
                "</xsi:Event>";

    @Before
    public void setup()
    {
        event = new CallCenterUpdateEvent();
    }


    @Test
    public void testReadEventFromXMLString() throws Exception
    {      
        event.readEventFromXMLString(CALL_CENTER_SUBSCRIPTION_EVENT_XML_RESULT);   
        
        assertEquals("b0c3233d-b145-4ebc-adf5-3acc37ad1ac3",event.getEventId());
        assertEquals("4",event.getSequenceNumber());
        assertEquals("Admin_Joseph@voip.tnltd.net",event.getUserId());
        assertEquals("CallCenterDashboard",event.getExternalApplicationId());
        assertEquals("5d116b25-c5d6-4481-ac36-a32277d63cd4",event.getSubscriptionId());
        assertEquals("roph0405@voip.tnltd.net",event.getTargetId());
        assertEquals(0, event.getAverageHandlingTime());
        assertEquals(0, event.getExpectedWaitTime());
        assertEquals(0, event.getAverageSpeedOfAnswer());
        assertEquals(0, event.getLongestWaitTime());
        assertEquals(0, event.getNumCallsInQueue());
        assertEquals(27, event.getNumAgentsAssigned());
        assertEquals(11, event.getNumAgentsStaffed());
        assertEquals(0, event.getNumStaffedAgentsIdle());
        assertEquals(9, event.getNumStaffedAgentsUnavailable());
    }    


    @Test
    public void testReadSubscriptionTerminationEventFromXMLString() throws Exception
    {      
        event.readEventFromXMLString(CALL_CENTER_SUBSCRIPTION_TERMINATION_XML_RESULT);   
        
        assertEquals("12d28bed-ae54-41a9-b76c-6fa021a926f8",event.getEventId());
        assertEquals("2",event.getSequenceNumber());
        assertEquals("gnolanAdmin1@xdp.broadsoft.com",event.getUserId());
        assertEquals("CallCenterDashboard",event.getExternalApplicationId());
        assertEquals("1050a793-010c-49be-9392-4cfb1b6d8afa",event.getSubscriptionId());
    }    

}
