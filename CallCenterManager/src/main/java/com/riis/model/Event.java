package com.riis.model;

import java.io.Serializable;
import java.io.StringReader;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.riis.model.events.EventData;
import com.riis.model.events.HTTPContact;


@XmlRootElement(name="Event")
@XmlAccessorType(XmlAccessType.FIELD)
public class Event  extends AbstractXMLParser implements XMLParserContract, Serializable
{
    private static final long serialVersionUID = 2014222924597455601L;

    @XmlElement(required=true)
    String eventId;
    @XmlElement(required=true)
    String sequenceNumber;
    @XmlElement(required=true)
    String userId;
    @XmlElement(required=true)
    String externalApplicationId;
    @XmlElement(required=true)
    String subscriptionId;
    @XmlElement(required=true)
    HTTPContact httpContact;
    @XmlElement(required=true)
    String targetId;
    @XmlElement(required=true)
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

    @Override
    public void readIdFromXMLNode(Node element)
    {
        // TODO Auto-generated method stub
        
    }
    
    public void readAgentEventFromXMLString(String agentEventXML)
    {
        try
        {
            Document doc = docBuilder.parse(new InputSource(new StringReader(agentEventXML)));
            doc.getDocumentElement().normalize();
            if (doc.getDocumentElement().getNodeName() != "Event")
            {
                System.out.println("ERROR - readAgentEventFromXMLString: " + agentEventXML);
                throw new Exception("Wrong Root Node: Expected Event, received " + doc.getDocumentElement().getNodeName());
            }
            eventId = getNodeValueWithPath("/Event/eventId");
            sequenceNumber = getNodeValueWithPath("/Event/sequenceNumber");
            userId = getNodeValueWithPath("/Event/userId");
            externalApplicationId = getNodeValueWithPath("/Event/externalApplicationId");
            subscriptionId = getNodeValueWithPath("/Event/subscriptionId");
        }
        catch (Exception e)
        {
            System.err.println("Error parsing XML from string! :" + e.getMessage());
            System.err.println("XML string :" + agentEventXML);
            e.printStackTrace();
        }
    }

}
