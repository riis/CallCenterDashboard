package com.riis.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="event")
@XmlAccessorType(XmlAccessType.FIELD)
public class Event
{
    @XmlElement(required=true)
    String sequenceNumber;
    @XmlElement(required=true)
    String subscriberId;
    @XmlElement(required=true)
    String applicationId;
    @XmlElement(required=true)
    String subscriptionId;
    @XmlElement(required=true)
    String targetId;
    @XmlElement(required=true)
    List<EventData> eventData;
    
    public Event() 
    {
        super();
    }

    public Event(String sequenceNumber, String subscriberId,
            String applicationId, String subscriptionId, String targetId,
            List<EventData> eventData) 
    {
        super();
        this.sequenceNumber = sequenceNumber;
        this.subscriberId = subscriberId;
        this.applicationId = applicationId;
        this.subscriptionId = subscriptionId;
        this.targetId = targetId;
        this.eventData = eventData;
    }
    
    public String getSequenceNumber() 
    {
        return sequenceNumber;
    }
    
    public void setSequenceNumber(String sequenceNumber) 
    {
        this.sequenceNumber = sequenceNumber;
    }
    
    public String getSubscriberId() 
    {
        return subscriberId;
    }
    
    public void setSubscriberId(String subscriberId) 
    {
        this.subscriberId = subscriberId;
    }
    
    public String getApplicationId() 
    {
        return applicationId;
    }
    
    public void setApplicationId(String applicationId) 
    {
        this.applicationId = applicationId;
    }
    
    public String getSubscriptionId() 
    {
        return subscriptionId;
    }
    
    public void setSubscriptionId(String subscriptionId) 
    {
        this.subscriptionId = subscriptionId;
    }
    
    public String getTargetId() 
    {
        return targetId;
    }
    
    public void setTargetId(String targetId) 
    {
        this.targetId = targetId;
    }
    
    public List<EventData> getEventData() 
    {
        return eventData;
    }
    
    public void setEventData(List<EventData> eventData) 
    {
        this.eventData = eventData;
    }
    
    public String toString()
    {
        StringBuffer buff = new StringBuffer("Event:\n");
        buff.append("    sequenceNumber:" + sequenceNumber + "\n");
        buff.append("    subscriberId:" + subscriberId + "\n");
        buff.append("    applicationId:" + applicationId + "\n");
        buff.append("    subscriptionId:" + subscriptionId + "\n");
        if (eventData != null && eventData.size() > 0)
        {
            buff.append("    eventData:" + eventData.toString() + "\n");            
        }
        else
        {
            buff.append("    eventData:NULL\n");
        }
        return buff.toString();
    }
}
