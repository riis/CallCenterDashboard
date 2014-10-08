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

    int averageHandlingTime;
    int expectedWaitTime;
    int averageSpeedOfAnswer;
    int longestWaitTime;
    int numCallsInQueue;
    int numAgentsAssigned;
    int numAgentsStaffed;
    int numStaffedAgentsIdle;
    int numStaffedAgentsUnavailable;
    
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
            String eventType = attributes.item(0).getTextContent();
            if ("xsi:CallCenterMonitoringEvent".equals(eventType))
            {
                nodelist = doc.getDocumentElement().getElementsByTagName("xsi:averageHandlingTime");
                averageHandlingTime = Integer.parseInt(getValueFromNode(nodelist));
                System.out.println("averageHandlingTime ="+averageHandlingTime);

                nodelist = doc.getDocumentElement().getElementsByTagName("xsi:expectedWaitTime");
                expectedWaitTime = Integer.parseInt(getValueFromNode(nodelist));
                System.out.println("expectedWaitTime ="+expectedWaitTime);

                nodelist = doc.getDocumentElement().getElementsByTagName("xsi:averageSpeedOfAnswer");
                averageSpeedOfAnswer = Integer.parseInt(getValueFromNode(nodelist));
                System.out.println("averageSpeedOfAnswer ="+averageSpeedOfAnswer);

                nodelist = doc.getDocumentElement().getElementsByTagName("xsi:longestWaitTime");
                longestWaitTime = Integer.parseInt(getValueFromNode(nodelist));
                System.out.println("longestWaitTime ="+longestWaitTime);

                nodelist = doc.getDocumentElement().getElementsByTagName("xsi:numCallsInQueue");
                numCallsInQueue = Integer.parseInt(getValueFromNode(nodelist));
                System.out.println("numCallsInQueue ="+numCallsInQueue);

                nodelist = doc.getDocumentElement().getElementsByTagName("xsi:numAgentsAssigned");
                numAgentsAssigned = Integer.parseInt(getValueFromNode(nodelist));
                System.out.println("numAgentsAssigned ="+numAgentsAssigned);

                nodelist = doc.getDocumentElement().getElementsByTagName("xsi:numAgentsStaffed");
                numAgentsStaffed = Integer.parseInt(getValueFromNode(nodelist));
                System.out.println("numAgentsStaffed ="+numAgentsStaffed);

                nodelist = doc.getDocumentElement().getElementsByTagName("xsi:numStaffedAgentsIdle");
                numStaffedAgentsIdle = Integer.parseInt(getValueFromNode(nodelist));
                System.out.println("numStaffedAgentsIdle ="+numStaffedAgentsIdle);

                nodelist = doc.getDocumentElement().getElementsByTagName("xsi:numStaffedAgentsUnavailable");
                numStaffedAgentsUnavailable = Integer.parseInt(getValueFromNode(nodelist));
                System.out.println("numStaffedAgentsUnavailable ="+numStaffedAgentsUnavailable);
            }
            else if ("xsi:SubscriptionTerminatedEvent".equals(eventType))
            {
                System.out.println("SubscriptionTerminationEvent - re-subscribe here");
            }
            else
            {
                System.out.println("Unknown Event: "+ eventType);                
            }
             
         }
        catch (Exception e)
        {
            System.err.println("Error parsing XML from string! :" + e.getMessage());
            e.printStackTrace();
        }
    }

}
