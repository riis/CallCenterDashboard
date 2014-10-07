package com.riis.model;

import java.io.Serializable;
import java.io.StringReader;

import javax.xml.bind.annotation.XmlElement;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class SubscriptionUpdateEvent extends AbstractXMLParser implements XMLParserContract, Serializable
{
    @XmlElement(name="xsi:eventId", required=true)
    String eventId;
    @XmlElement(name="xsi:sequenceNumber", required=true)
    String sequenceNumber;
    @XmlElement(name="xsi:userId", required=true)
    String userId;
    @XmlElement(name="xsi:externalApplicationId", required=true)
    String externalApplicationId;
    @XmlElement(name="xsi:subscriptionId", required=true)
    String subscriptionId;
    @XmlElement(name="xsi:targetId", required=true)
    String targetId;

    public SubscriptionUpdateEvent()
    {    
    }
    
    
    public SubscriptionUpdateEvent(String eventId, String sequenceNumber, String userId,
            String externalApplicationId, String subscriptionId, String targetId)
    {
        this.eventId = eventId;
        this.sequenceNumber = sequenceNumber;
        this.userId = userId;
        this.externalApplicationId = externalApplicationId;
        this.subscriptionId = subscriptionId;
        this.targetId = targetId;
    }
    
    public String getEventId() 
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
    
    public String getTargetId()
    {
        return targetId;
    }

    public void setTargetId(String targetId)
    {
        this.targetId = targetId;
    }
    
    @Override
    public void readIdFromXMLNode(Node element)
    {
        // TODO Auto-generated method stub
        
    }
    
    protected String getValueFromNode(NodeList nodeList)
    {
        String retVal = null;
        System.out.println("Nodelist.size = " + nodeList.getLength());
        if(nodeList != null && nodeList.getLength() == 1)
        {
            retVal =  nodeList.item(0).getTextContent();
        }
        System.out.println("RetVal = " + retVal);
        return retVal;
    }

    public void readEventFromXMLString(String eventXML)
    {
        try
        {
            Document doc = docBuilder.parse(new InputSource(new StringReader(eventXML)));
            doc.getDocumentElement().normalize();
            if (!"xsi:Event".equals(doc.getDocumentElement().getNodeName()))
            {
                System.out.println("ERROR - readEventFromXMLString: " + eventXML);
                throw new Exception("Wrong Root Node: Expected Event, received " + doc.getDocumentElement().getNodeName());
            }
            
            NodeList nodelist = doc.getDocumentElement().getElementsByTagName("xsi:eventID");
            eventId = getValueFromNode(nodelist);
            
            nodelist = doc.getDocumentElement().getElementsByTagName("xsi:sequenceNumber");
            sequenceNumber = getValueFromNode(nodelist);
            
            nodelist = doc.getDocumentElement().getElementsByTagName("xsi:userId");
            userId = getValueFromNode(nodelist);
            
            nodelist = doc.getDocumentElement().getElementsByTagName("xsi:externalApplicationId");
            externalApplicationId = getValueFromNode(nodelist);
            
            nodelist = doc.getDocumentElement().getElementsByTagName("xsi:subscriptionId");
            subscriptionId = getValueFromNode(nodelist);
            
            nodelist = doc.getDocumentElement().getElementsByTagName("xsi:targetId");
            targetId = getValueFromNode(nodelist);
        }
        catch (Exception e)
        {
            System.err.println("Error parsing XML from string! :" + e.getMessage());
            e.printStackTrace();
        }
     
    }


}
