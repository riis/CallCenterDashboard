package com.riis.model;

import java.io.Serializable;
import java.io.StringReader;
import java.util.ArrayList;
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

@XmlRootElement(name="callcenter")
@XmlAccessorType(XmlAccessType.FIELD)
public class CallCenter extends AbstractXMLParser implements XMLParserContract, Serializable
{
    private static final long serialVersionUID = -7087245582724123433L;
    private static String NODE_NAME = "serviceUserID";
    @XmlElement(required=false)
    private String callCenterId;
    @XmlElement(required=false)
    private String callCenterName;
    @XmlElement(required=false)
    private int queueLength;
    private String domain;
    private String subscriptionId;
    
    private Model model;


    public CallCenter(String domain)
    {
        this.domain = domain;
        System.out.println("CallCenter - set domain to " + this.domain);        
    }
    
    public String getCallCenterId()
    {
        return callCenterId;
    }
    
    
    public void setCallCenterId(String callCenterId)
    {
        if (callCenterId.indexOf('@') == -1)
        {
            this.callCenterId = callCenterId + domain;
            System.out.println("Appended domain and Set Call Center Id to " + this.callCenterId);
        }
        else
        {
            this.callCenterId = callCenterId;            
            System.out.println("Set Call Center Id to " + this.callCenterId);
        }
    }
    
    
    public String getCallCenterName()
    {
        return callCenterName;
    }
    
    
    public void setCallCenterName(String callCenterName)
    {
        this.callCenterName = callCenterName;
    }
    
    
    public int getQueueLength()
    {
        return queueLength;
    }
    
    
    public void setQueueLength(int queueLength)
    {
        this.queueLength = queueLength;
    }
    
    
    public String getSubscriptionId()
    {
        return subscriptionId;
    }

    
    public void setSubscriptionId(String subscriptionId)
    {
        this.subscriptionId = subscriptionId;
    }


    public void readIdFromXMLNode(Node element)
    {
        try
        {
            if (NODE_NAME.equals(element.getNodeName()))
            {
                setCallCenterId(element.getTextContent());                           
            }
        }
        catch (Exception e)
        {
            System.err.println("Error parsing XML from string! :" + e.getMessage());
            e.printStackTrace();                       
        }        
    }
    
    
    public void readNameFromXMLString(String callCenterProfile)
    {
        try
        {
            doc = docBuilder.parse(new InputSource(new StringReader(callCenterProfile)));
            doc.getDocumentElement().normalize();
            if (doc.getDocumentElement().getNodeName() != "ACDProfile")
            {
                throw new Exception("Wrong Root Node: Expected ACDProfile, received " + doc.getDocumentElement().getNodeName());
            }
            callCenterName = getNodeValueWithPath("/ACDProfile/serviceInstanceProfile/name");
        }
        catch (Exception e)
        {
            System.err.println("Error parsing XML from string! :" + e.getMessage());
            e.printStackTrace();                       
        }
        
    }

    public void readQueueLengthFromXMLString(String callCenterCalls)
    {
        try
        {
            if (callCenterCalls != null && callCenterCalls.length()>1)
            {
                doc = docBuilder.parse(new InputSource(new StringReader(callCenterCalls)));
                doc.getDocumentElement().normalize();
                NodeList nodes = doc.getDocumentElement().getElementsByTagName("queueEntry");   
                queueLength = nodes.getLength();                
            }
        }
        catch (Exception e)
        {
            System.err.println("Error parsing XML from string! :" + e.getMessage());
            e.printStackTrace();                       
        }
    }

   
    public List<CallCenter> createListFromXMLString(String broadsoftXML)
    {
        List<CallCenter> callCenters = new ArrayList<CallCenter>();
        try
        {
            Document doc = docBuilder.parse(new InputSource(new StringReader(broadsoftXML)));
            doc.getDocumentElement().normalize();
            if (doc.getDocumentElement().getNodeName() != "CallCenters")
            {
                System.out.println("ERROR - createListFromXMLString: " + broadsoftXML);
                throw new Exception("Wrong Root Node: Expected CallCenters, received " + doc.getDocumentElement().getNodeName());
            }

            NodeList nodes = doc.getDocumentElement().getElementsByTagName("serviceUserID");
            for (int i=0; i<nodes.getLength(); i++)
            {
                Element element = (Element)nodes.item(i);
                CallCenter callCenterToAdd = new CallCenter(domain);
                callCenterToAdd.readIdFromXMLNode(element);
                callCenters.add(callCenterToAdd);                 
            }
        }
        catch (Exception e)
        {
            System.err.println("Error parsing XML from string! :" + e.getMessage());
            System.err.println("XML string :" + broadsoftXML);
            e.printStackTrace();
        }
        return callCenters;
    }

    
    public void parseSubscriptionXMLString(String callCenterSubscriptionXML)
    {        
        try
        {
            Document doc = docBuilder.parse(new InputSource(new StringReader(callCenterSubscriptionXML)));
            doc.getDocumentElement().normalize();
            if (doc.getDocumentElement().getNodeName() != "Subscription")
            {
                System.out.println("ERROR - parseSubscriptionXMLString: " + callCenterSubscriptionXML);
                throw new Exception("Wrong Root Node: Expected Subscription, received " + doc.getDocumentElement().getNodeName());
            }

            NodeList nodelist = doc.getDocumentElement().getElementsByTagName("subscriptionId");
            subscriptionId = getValueFromNode(nodelist);
        }
        catch (Exception e)
        {
            System.err.println("Error parsing XML from string! :" + e.getMessage());
            System.err.println("XML string :" + callCenterSubscriptionXML);
            e.printStackTrace();
        }
    }
    
    public void updateFromEvent(CallCenterUpdateEvent event)
    {
        queueLength = event.getNumCallsInQueue();
    }

}
