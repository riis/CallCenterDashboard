package com.riis.model;

import java.io.Serializable;
import java.io.StringReader;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;



@XmlRootElement(name="xsi:Event")
@XmlAccessorType(XmlAccessType.FIELD)
public class AgentUpdateEvent  extends  SubscriptionUpdateEvent
{
    private static final long serialVersionUID = 2014222924597455601L;
    
    public static final String AGENT_STATE_EVENT = "xsi:AgentStateEvent";
    public static final String AGENT_SUBSCRIPTION_TERMINATION_EVENT = "xsi:SubscriptionTerminatedEvent";
    public static final String AGENT_SUBSCRIPTION_EVENT = "xsi:AgentSubscriptionEvent";
    public static final String AGENT_UNKNOWN_EVENT = "UNKNOWN";

    @XmlElement(name="xsi:state", required=true)
    String state;
    @XmlElement(name="xsi:stateTimestamp", required=true)
    Date stateTimestamp;
    @XmlElement(name="xsi:signInTimestamp", required=true)
    Date signInTimestamp;
    @XmlElement(name="xsi:totalAvailableTime", required=true)
    Long totalAvailableTime;
    @XmlElement(name="xsi:averageWrapUpTime", required=true)
    Long averageWrapUpTime;

    private String eventType;
    
    public AgentUpdateEvent() 
    {
        super();
    }

    public AgentUpdateEvent(String eventId, String sequenceNumber, String userId,
            String externalApplicationId, String subscriptionId, String targetId, 
            String state, Date stateTimestamp, Date signInTimestamp, Long totalAvailableTime,
            Long averageWrapUpTime) 
    {
        super(eventId, sequenceNumber, userId, externalApplicationId, subscriptionId, targetId);
        this.state = state;
        this.stateTimestamp = stateTimestamp;
        this.signInTimestamp = signInTimestamp;
        this.totalAvailableTime = totalAvailableTime;
        this.averageWrapUpTime = averageWrapUpTime;
    }
    
    public String getState()
    {
        return state;
    }

    public void setState(String state)
    {
        this.state = state;
    }

    public Date getStateTimestamp()
    {
        return stateTimestamp;
    }

    public void setStateTimestamp(Date stateTimestamp)
    {
        this.stateTimestamp = stateTimestamp;
    }

    public Date getSignInTimestamp()
    {
        return signInTimestamp;
    }

    public void setSignInTimestamp(Date signInTimestamp)
    {
        this.signInTimestamp = signInTimestamp;
    }

    public Long getTotalAvailableTime()
    {
        return totalAvailableTime;
    }

    public void setTotalAvailableTime(Long totalAvailableTime)
    {
        this.totalAvailableTime = totalAvailableTime;
    }

    public Long getAverageWrapUpTime()
    {
        return averageWrapUpTime;
    }

    public void setAverageWrapUpTime(Long averageWrapUpTime)
    {
        this.averageWrapUpTime = averageWrapUpTime;
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
        buff.append("    state:" + state + "\n");    
        buff.append("    stateTimestamp:" + stateTimestamp + "\n");    
        buff.append("    signInTimestamp:" + signInTimestamp + "\n");    
        buff.append("    totalAvailableTime:" + totalAvailableTime + "\n");    
        buff.append("    averageWrapUpTime:" + averageWrapUpTime + "\n");    
        return buff.toString();
    }

    public String toPusherJSONFormat()
    {
        StringBuffer buff = new StringBuffer("{\"eventId\":\"" + eventId + "\",");
        buff.append("\"sequenceNumber\":\"" + sequenceNumber + "\",");
        buff.append("\"subscriptionId\":\"" + subscriptionId + "\",");
        buff.append("\"targetId\":\"" + targetId + "\",");
        buff.append("\"state\":\"" + state + "\",");            
        buff.append("\"stateTimestamp\":\"" + stateTimestamp + "\",");    
        buff.append("\"signInTimestamp\":\"" + signInTimestamp + "\",");    
        buff.append("\"totalAvailableTime\":\"" + totalAvailableTime + "\"");    
        buff.append("}");
        return buff.toString();
    }
    
    public void readEventFromXMLString(String agentEventXML)
    {
        try
        {
            super.readEventFromXMLString(agentEventXML);

            Document doc = docBuilder.parse(new InputSource(new StringReader(agentEventXML)));
            doc.getDocumentElement().normalize();
            
            NodeList nodelist = doc.getDocumentElement().getElementsByTagName("xsi:eventData");
            NamedNodeMap attributes =  nodelist.item(0).getAttributes();
            eventType = attributes.item(0).getTextContent();
            if (AGENT_STATE_EVENT.equals(eventType))
            {                
                System.out.println("Received Agent State Event");                
                nodelist = doc.getDocumentElement().getElementsByTagName("xsi:state");
                state = getValueFromNode(nodelist);
                System.out.println("XXXX: Set agent state to " + state );
    
                nodelist = doc.getDocumentElement().getElementsByTagName("xsi:stateTimestamp");
                String nodeValue = getValueFromNode(nodelist);
                if (nodeValue != null)
                {
                    Date tmpStateTimestamp = new Date();
                    tmpStateTimestamp.setTime(Long.parseLong(nodeValue));
                    setStateTimestamp(tmpStateTimestamp);                    
                }
    
                nodelist = doc.getDocumentElement().getElementsByTagName("xsi:signInTimestamp");
                nodeValue = getValueFromNode(nodelist);
                if (nodeValue != null)
                {
                    Date tmpSignIntimestamp = new Date();
                    tmpSignIntimestamp.setTime(Long.parseLong(nodeValue));
                    setSignInTimestamp(tmpSignIntimestamp);
                    
                }
    
                nodelist = doc.getDocumentElement().getElementsByTagName("xsi:totalAvailableTime");
                nodeValue = getValueFromNode(nodelist);
                if (nodeValue != null)
                {
                    totalAvailableTime = Long.parseLong(nodeValue);                    
                }
    
                nodelist = doc.getDocumentElement().getElementsByTagName("xsi:averageWrapUpTime");
                nodeValue = getValueFromNode(nodelist);
                if (nodeValue != null)
                {
                    averageWrapUpTime = Long.parseLong(getValueFromNode(nodelist));  
                }
            }
            else if (AGENT_SUBSCRIPTION_TERMINATION_EVENT.equals(eventType))
            {
                System.out.println("SubscriptionTerminationEvent - will re-subacribe in web-service endpoint");
            }
            else if (AGENT_SUBSCRIPTION_EVENT.equals(eventType))
            {
                System.out.println("AgentSubscriptionEvent - subscribed");                
            }
            else
            {
                System.out.println("Unknown Event: "+ eventType);  
                eventType = AGENT_SUBSCRIPTION_EVENT;
            }
             
         }
        catch (Exception e)
        {
            System.err.println("Error parsing XML from string! :" + e.getMessage());
            e.printStackTrace();
        }
    }
    
}
