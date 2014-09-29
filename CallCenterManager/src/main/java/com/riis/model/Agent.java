package com.riis.model;

import java.io.Serializable;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class Agent extends AbstractXMLParser implements XMLParserContract, Serializable
{
    private static final long serialVersionUID = 2243830860628688482L;
    private static String NODE_NAME = "userDetails";
    private String agentId;
    private String name;
    private String phoneNumber;
    private String extension;
    private String status;      // "Unavailable", "Available", "On-Call", "Sign-Out"
    private String callCenterId;
    private Date statusChangedTimestamp;
    
    public String getAgentId()
    {
        return agentId;
    }
    
    
    public void setAgentId(String agentId)
    {
        this.agentId = agentId;
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
        this.callCenterId = callCenterId;
    }
    
    
    public Date getStatusChangedTimestamp()
    {
        return statusChangedTimestamp;
    }
    
    
    public void setStatusChangedTimestamp(Date statusChangedTimestamp)
    {
        this.statusChangedTimestamp = statusChangedTimestamp;
    }


    public void readIdFromXMLNode(Node element)
    {
        try
        {
            if (NODE_NAME.equals(element.getNodeName()))
            {
                agentId = readFromNodeWithPath(element, "userId");
                name = readFromNodeWithPath(element, "firstName") + " " + readFromNodeWithPath(element, "lastName");
                phoneNumber = readFromNodeWithPath(element, "number");
                extension = readFromNodeWithPath(element, "extension");
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
                    Agent newAgent = new Agent();
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
}
