package com.riis.model.events;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name="Event")
@XmlAccessorType(XmlAccessType.FIELD)
public class Event implements Serializable
{
    @XmlElement(required=false)
    String eventId;
    @XmlElement(required=false)
    String sequenceNumber;
    @XmlElement(required=false)
    String userId;
    @XmlElement(required=false)
    String externalApplicationId;
    @XmlElement(required=false)
    String subscriptionId;
    @XmlElement(required = false)
    HTTPContact httpContact;
    @XmlElement(required=false)
    String targetId;
    @XmlElement(required=false)
    List<EventData> eventData;
    
    public Event() 
    {
        super();
    }

    public Event(String eventId, String sequenceNumber, String userId,
            String externalApplicationId, String subscriptionId, HTTPContact httpContact,
            String targetId, List<EventData> eventData) 
    {
        super();
        this.eventId = eventId;
        this.sequenceNumber = sequenceNumber;
        this.userId = userId;
        this.externalApplicationId = externalApplicationId;
        this.subscriptionId = subscriptionId;
        this.httpContact = httpContact;
        this.targetId = targetId;
        this.eventData = eventData;
    }
    
    public String getEventIf() 
    {
        return eventId;
    }
    
    public void setEventId(String eventId) 
    {
        this.eventId = eventId;
    }
    
    public String getSequenceNumber() 
    {
        return sequenceNumber;
    }
    
    public void setSequenceNumber(String sequenceNumber) 
    {
        this.sequenceNumber = sequenceNumber;
    }
    
    public String getUserId() 
    {
        return userId;
    }
    
    public void setUserId(String userId) 
    {
        this.userId = userId;
    }
    
    public String getExternalApplicationId() 
    {
        return externalApplicationId;
    }
    
    public void setExternalApplicationId(String externalApplicationId) 
    {
        this.externalApplicationId = externalApplicationId;
    }
    
    public String getSubscriptionId() 
    {
        return subscriptionId;
    }
    
    public void setSubscriptionId(String subscriptionId) 
    {
        this.subscriptionId = subscriptionId;
    }
    
    public HTTPContact getHttpContact()
    {
        return httpContact;
    }

    public void setHttpContact(HTTPContact httpContact)
    {
        this.httpContact = httpContact;
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
        buff.append("    eventId:" + eventId + "\n");
        buff.append("    sequenceNumber:" + sequenceNumber + "\n");
        buff.append("    userId:" + userId + "\n");
        buff.append("    externalApplicationId:" + externalApplicationId + "\n");
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
