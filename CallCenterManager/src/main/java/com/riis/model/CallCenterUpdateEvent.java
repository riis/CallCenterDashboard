package com.riis.model;

import java.io.StringReader;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

@XmlRootElement(name="xsi:Event")
@XmlAccessorType(XmlAccessType.FIELD)
public class CallCenterUpdateEvent extends SubscriptionUpdateEvent
{
    private static final long serialVersionUID = 5132003256033781959L;

    public static final String CALL_CENTER_STATE_EVENT = "xsi:CallCenterMonitoringEvent";
    public static final String CALL_CENTER_SUBSCRIPTION_TERMINATION_EVENT = "xsi:SubscriptionTerminatedEvent";
    public static final String CALL_CENTER_UNKNOWN_EVENT = "UNKNOWN";

    int averageHandlingTime;
    int expectedWaitTime;
    int averageSpeedOfAnswer;
    int longestWaitTime;
    int numCallsInQueue;
    int numAgentsAssigned;
    int numAgentsStaffed;
    int numStaffedAgentsIdle;
    int numStaffedAgentsUnavailable;
    
    private String eventType;
    
    public CallCenterUpdateEvent() 
    {
        super();
    }

    public CallCenterUpdateEvent(String eventId, String sequenceNumber, String userId,
            String externalApplicationId, String subscriptionId, String targetId, 
            int averageHandlingTime, int expectedWaitTime, int averageSpeedOfAnswer,
            int longestWaitTime, int numCallsInQueue, int numAgentsAssigned,
            int numAgentsStaffed, int numStaffedAgentsIdle, int numStaffedAgentsUnavailable) 
    {
        super(eventId, sequenceNumber, userId, externalApplicationId, subscriptionId, targetId);
        this.averageHandlingTime = averageHandlingTime;
        this.expectedWaitTime = expectedWaitTime;
        this.averageSpeedOfAnswer = averageSpeedOfAnswer;
        this.longestWaitTime = longestWaitTime;
        this.numCallsInQueue = numCallsInQueue;
        this.numAgentsAssigned = numAgentsAssigned;
        this.numAgentsStaffed = numAgentsStaffed;
        this.numStaffedAgentsIdle = numStaffedAgentsIdle;
        this.numStaffedAgentsUnavailable = numStaffedAgentsUnavailable;
    }

    public int getAverageHandlingTime()
    {
        return averageHandlingTime;
    }

    public void setAverageHandlingTime(int averageHandlingTime)
    {
        this.averageHandlingTime = averageHandlingTime;
    }

    public int getExpectedWaitTime()
    {
        return expectedWaitTime;
    }

    public void setExpectedWaitTime(int expectedWaitTime)
    {
        this.expectedWaitTime = expectedWaitTime;
    }

    public int getAverageSpeedOfAnswer()
    {
        return averageSpeedOfAnswer;
    }

    public void setAverageSpeedOfAnswer(int averageSpeedOfAnswer)
    {
        this.averageSpeedOfAnswer = averageSpeedOfAnswer;
    }

    public int getLongestWaitTime()
    {
        return longestWaitTime;
    }

    public void setLongestWaitTime(int longestWaitTime)
    {
        this.longestWaitTime = longestWaitTime;
    }

    public int getNumCallsInQueue()
    {
        return numCallsInQueue;
    }

    public void setNumCallsInQueue(int numCallsInQueue)
    {
        this.numCallsInQueue = numCallsInQueue;
    }

    public int getNumAgentsAssigned()
    {
        return numAgentsAssigned;
    }

    public void setNumAgentsAssigned(int numAgentsAssigned)
    {
        this.numAgentsAssigned = numAgentsAssigned;
    }

    public int getNumAgentsStaffed()
    {
        return numAgentsStaffed;
    }

    public void setNumAgentsStaffed(int numAgentsStaffed)
    {
        this.numAgentsStaffed = numAgentsStaffed;
    }

    public int getNumStaffedAgentsIdle()
    {
        return numStaffedAgentsIdle;
    }

    public void setNumStaffedAgentsIdle(int numStaffedAgentsIdle)
    {
        this.numStaffedAgentsIdle = numStaffedAgentsIdle;
    }

    public int getNumStaffedAgentsUnavailable()
    {
        return numStaffedAgentsUnavailable;
    }

    public void setNumStaffedAgentsUnavailable(int numStaffedAgentsUnavailable)
    {
        this.numStaffedAgentsUnavailable = numStaffedAgentsUnavailable;
    }
    
    // Only need getter for event type - it should be set automatically when parsing an event
    public String getEventType()
    {
        return eventType;
    }

    public String toString()
    {
        StringBuffer buff = new StringBuffer("Event:\n");
        buff.append("    eventId:" + eventId + "\n");
        buff.append("    sequenceNumber:" + sequenceNumber + "\n");
        buff.append("    userId:" + userId + "\n");
        buff.append("    externalApplicationId:" + externalApplicationId + "\n");
        buff.append("    subscriptionId:" + subscriptionId + "\n");
        buff.append("    targetId: " + targetId + "\n");
        buff.append("    averageHandlingTime:" + averageHandlingTime + "\n");
        buff.append("    expectedWaitTime:" + expectedWaitTime + "\n");
        buff.append("    averageSpeedOfAnswer:" + averageSpeedOfAnswer + "\n");
        buff.append("    longestWaitTime:" + longestWaitTime + "\n");
        buff.append("    numCallsInQueue:" + numCallsInQueue + "\n");
        buff.append("    numAgentsAssigned:" + numAgentsAssigned + "\n");
        buff.append("    numAgentsStaffed:" + numAgentsStaffed + "\n");
        buff.append("    numStaffedAgentsIdle:" + numStaffedAgentsIdle + "\n");
        buff.append("    numStaffedAgentsUnavailable:" + numStaffedAgentsUnavailable + "\n");
        return buff.toString();
    }

    public String toPusherJSONFormat()
    {
        StringBuffer buff = new StringBuffer("{\"eventId\":\"" + eventId + "\",");
        buff.append("\"sequenceNumber\":\"" + sequenceNumber + "\",");
        buff.append("\"subscriptionId\":\"" + subscriptionId + "\",");
        buff.append("\"targetId\":\"" + targetId + "\",");
        buff.append("\"numCallsInQueue\":\"" + numCallsInQueue + "\"");            
        buff.append("}");
        return buff.toString();
    }
    
    public void readEventFromXMLString(String callCenterEventXML)
    {
        try
        {
            super.readEventFromXMLString(callCenterEventXML);

            Document doc = docBuilder.parse(new InputSource(new StringReader(callCenterEventXML)));
            doc.getDocumentElement().normalize();
            
            NodeList nodelist = doc.getDocumentElement().getElementsByTagName("xsi:eventData");
            NamedNodeMap attributes =  nodelist.item(0).getAttributes();
            eventType = attributes.item(0).getTextContent();
            if (CallCenterUpdateEvent.CALL_CENTER_STATE_EVENT.equals(eventType))
            {
                System.out.println("Reveiced CallCenter state update event");
                nodelist = doc.getDocumentElement().getElementsByTagName("xsi:averageHandlingTime");
                String nodeValue = getValueFromNode(nodelist);
                if (nodeValue != null)
                {
                    averageHandlingTime = Integer.parseInt(nodeValue);                    
                }

                nodelist = doc.getDocumentElement().getElementsByTagName("xsi:expectedWaitTime");
                nodeValue = getValueFromNode(nodelist);
                if (nodeValue != null)
                {
                    expectedWaitTime = Integer.parseInt(nodeValue);                    
                }

                nodelist = doc.getDocumentElement().getElementsByTagName("xsi:averageSpeedOfAnswer");
                nodeValue = getValueFromNode(nodelist);
                if (nodeValue != null)
                {
                    averageSpeedOfAnswer = Integer.parseInt(nodeValue);                       
                }

                nodelist = doc.getDocumentElement().getElementsByTagName("xsi:longestWaitTime");
                nodeValue = getValueFromNode(nodelist);
                if (nodeValue != null)
                {
                    longestWaitTime = Integer.parseInt(nodeValue);                     
                }

                nodelist = doc.getDocumentElement().getElementsByTagName("xsi:numCallsInQueue");
                nodeValue = getValueFromNode(nodelist);
                if (nodeValue != null)
                {
                    numCallsInQueue = Integer.parseInt(nodeValue);                       
                }

                nodelist = doc.getDocumentElement().getElementsByTagName("xsi:numAgentsAssigned");
                nodeValue = getValueFromNode(nodelist);
                if (nodeValue != null)
                {
                    numAgentsAssigned = Integer.parseInt(nodeValue);                       
                }

                nodelist = doc.getDocumentElement().getElementsByTagName("xsi:numAgentsStaffed");
                nodeValue = getValueFromNode(nodelist);
                if (nodeValue != null)
                {
                    numAgentsStaffed = Integer.parseInt(nodeValue);                       
                }

                nodelist = doc.getDocumentElement().getElementsByTagName("xsi:numStaffedAgentsIdle");
                nodeValue = getValueFromNode(nodelist);
                if (nodeValue != null)
                {
                    numStaffedAgentsIdle = Integer.parseInt(nodeValue);                       
                }

                nodelist = doc.getDocumentElement().getElementsByTagName("xsi:numStaffedAgentsUnavailable");
                nodeValue = getValueFromNode(nodelist);
                if (nodeValue != null)
                {
                    numStaffedAgentsUnavailable = Integer.parseInt(nodeValue);                       
                }
            }
            else if (CallCenterUpdateEvent.CALL_CENTER_SUBSCRIPTION_TERMINATION_EVENT.equals(eventType))
            {
                System.out.println("SubscriptionTerminationEvent - will re-subscribe in web-service");
            }
            else
            {
                System.out.println("Unknown Event: "+ eventType);   
                eventType = CallCenterUpdateEvent.CALL_CENTER_UNKNOWN_EVENT; 
            }
             
         }
        catch (Exception e)
        {
            System.err.println("Error parsing XML from string! :" + e.getMessage());
            e.printStackTrace();
        }
    }

}
