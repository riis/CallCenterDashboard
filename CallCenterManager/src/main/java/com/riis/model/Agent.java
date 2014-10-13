package com.riis.model;

import java.io.Serializable;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
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

@XmlRootElement(name="agent")
@XmlAccessorType(XmlAccessType.FIELD)
public class Agent extends AbstractXMLParser implements XMLParserContract, Serializable
{
    public static final String AGENT_UNAVAILABLE_STATUS = "Unavailable";
    public static final String AGENT_AVAILABLE_STATUS = "Available";
    public static final String AGENT_ONCALL_STATUS = "On-Call";
    public static final String AGENT_SIGNOUT_STATUS = "Sign-Out";
    
    private static final long serialVersionUID = 2243830860628688482L;
    private static String NODE_NAME = "userDetails";
    @XmlElement(required=false)
    private String agentId;
    @XmlElement(required=false)
    private String name;
    @XmlElement(required=false)
    private String phoneNumber;
    @XmlElement(required=false)
    private String extension;
    @XmlElement(required=false)
    private String status;      // "Unavailable", "Available", "On-Call", "Sign-Out"
    @XmlElement(required=false)
    private String callCenterId;
    @XmlElement(required=false)
    private Date statusChangedTimestamp;
    private String domain;
    private String subscriptionId;
    
    
    public Agent(String domain)
    {
        this.domain = domain;
        System.out.println("Agent - set domain to " + this.domain);        
    }
    
    private String appendDomain(String subject)
    {
        if (subject.indexOf('@') == -1)
        {
            System.out.println("Appended domain and Set Id to " + subject + domain);
            return (subject + domain);
        }
        else
        {
            System.out.println("Set Id to " + subject);
            return subject;            
        }       
    }
    
    public String getAgentId()
    {
        return agentId;
    }
    
    
    public void setAgentId(String agentId)
    {
        this.agentId = appendDomain(agentId);
    }
    
    
    public String getName()
    {
        return name;
    }
    
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    
    public String getPhoneNumber()
    {
        return phoneNumber;
    }
    
    
    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }
    
    
    public String getExtension()
    {
        return extension;
    }
    
    
    public void setExtension(String extension)
    {
        this.extension = extension;
    }
    
    
    public String getStatus()
    {
        return status;
    }
    
    
    public void setStatus(String status)
    {
        this.status = status;
    }
    
    
    public String getCallCenterId()
    {
        return callCenterId;
    }
    
    
    public void setCallCenterId(String callCenterId)
    {
        this.callCenterId = appendDomain(callCenterId);
    }
    
    
    public Date getStatusChangedTimestamp()
    {
        return statusChangedTimestamp;
    }
    
    
    public void setStatusChangedTimestamp(Date statusChangedTimestamp)
    {
        this.statusChangedTimestamp = statusChangedTimestamp;
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
                setAgentId(readFromNodeWithPath(element, "userId"));
                setName(readFromNodeWithPath(element, "firstName") + " " + readFromNodeWithPath(element, "lastName"));
                setPhoneNumber(readFromNodeWithPath(element, "number"));
                setExtension(readFromNodeWithPath(element, "extension"));
            }
        }
        catch (Exception e)
        {
            System.err.println("Error parsing XML from string! :" + e.getMessage());
            e.printStackTrace();                       
        }        
    }


    public List<Agent> createListFromXMLString(String agentXML)
    {
        List<Agent> agents = new ArrayList<Agent>();
        try
        {
            doc = docBuilder.parse(new InputSource(new StringReader(agentXML)));
            doc.getDocumentElement().normalize();
            NodeList callCenters = doc.getDocumentElement().getElementsByTagName("callCenter");   
            for (int i=0; i<callCenters.getLength(); i++)
            {
                Element callCenterElement = (Element)callCenters.item(i); 
                
                String callCenterId = readFromNodeWithPath(callCenterElement, "serviceUserID");
                NodeList agentsInCallCenter = callCenterElement.getElementsByTagName("userDetails"); 
                for (int j=0; j<agentsInCallCenter.getLength(); j++)
                {
                    Element agentElement = (Element)agentsInCallCenter.item(j); 
                    Agent newAgent = new Agent(domain);
                    newAgent.setCallCenterId(callCenterId);
                    newAgent.readIdFromXMLNode(agentElement);
                    agents.add(newAgent);
                }
            }
        }
        catch (Exception e)
        {
            System.err.println("Error parsing XML from string! :" + e.getMessage());
            e.printStackTrace();                       
        }
        return agents;
    }


    public void readStatusFromXMLString(String statusXML)
    {
        try
        {
            doc = docBuilder.parse(new InputSource(new StringReader(statusXML)));
            doc.getDocumentElement().normalize();
            if (doc.getDocumentElement().getNodeName() != "CallCenter")
            {
                System.out.println("ERROR Parsing Agent XML: " + statusXML);
                throw new Exception("Wrong Root Node: Expected CallCenter, received " + doc.getDocumentElement().getNodeName());
            }
            status = getNodeValueWithPath("/CallCenter/agentACDState");
        }
        catch (Exception e)
        {
            System.err.println("Error parsing XML from string! :" + e.getMessage());
            e.printStackTrace();                       
        }
    }


    public void parseSubscriptionXMLString(String agentSubscriptionXML)
    {
        try
        {
            Document doc = docBuilder.parse(new InputSource(new StringReader(agentSubscriptionXML)));
            doc.getDocumentElement().normalize();
            if (doc.getDocumentElement().getNodeName() != "Subscription")
            {
                System.out.println("ERROR - parseSubscriptionXMLString: " + agentSubscriptionXML);
                throw new Exception("Wrong Root Node: Expected Subscription, received " + doc.getDocumentElement().getNodeName());
            }
            NodeList nodelist = doc.getDocumentElement().getElementsByTagName("subscriptionId");
            subscriptionId = getValueFromNode(nodelist);
            System.out.println("Added subscription id " + subscriptionId + "To Agent " + name);
        }
        catch (Exception e)
        {
            System.err.println("Error parsing XML from string! :" + e.getMessage());
            System.err.println("XML string :" + agentSubscriptionXML);
            e.printStackTrace();
        }        
    }
    
    
    public void updateFromEvent(AgentUpdateEvent event)
    {
        status = event.getState();
    }
    
    
    public void parseCallsFromXML(String callsXML)
    {
        System.out.println("Calls XML = " + callsXML);
        try
        {
            doc = docBuilder.parse(new InputSource(new StringReader(callsXML)));
            doc.getDocumentElement().normalize();
            if (doc.getDocumentElement().getNodeName() != "Calls")
            {
                System.out.println("ERROR Parsing Agent XML: " + callsXML);
                throw new Exception("Wrong Root Node: Expected Calls, received " + doc.getDocumentElement().getNodeName());
            }
            NodeList nodelist = doc.getDocumentElement().getElementsByTagName("call");
            if (nodelist.getLength() > 0)
            {
                setStatus(Agent.AGENT_ONCALL_STATUS);
            }
        }
        catch (Exception e)
        {
            System.err.println("Error parsing XML from string! :" + e.getMessage());
            e.printStackTrace();                       
        }
    }



}
