package com.riis.model;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class CallCenter extends AbstractXMLParser implements XMLParserContract
{
    private static String NODE_NAME = "serviceUserID";
    private String callCenterId;
    private String callCenterName;
    private int queueLength;
    
    public String getCallCenterId()
    {
        return callCenterId;
    }
    
    
    public void setCallCenterId(String callCenterId)
    {
        this.callCenterId = callCenterId;
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
    
    
    public void readIdFromXMLNode(Node element)
    {
        try
        {
            if (NODE_NAME.equals(element.getNodeName()))
            {
                callCenterId = element.getTextContent();                           
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
            doc = docBuilder.parse(new InputSource(new StringReader(callCenterCalls)));
            doc.getDocumentElement().normalize();
            NodeList nodes = doc.getDocumentElement().getElementsByTagName("queueEntry");   
            queueLength = nodes.getLength();
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
                throw new Exception("Wrong Root Node: Expected CallCenters, received " + doc.getDocumentElement().getNodeName());
            }

            NodeList nodes = doc.getDocumentElement().getElementsByTagName("serviceUserID");
            for (int i=0; i<nodes.getLength(); i++)
            {
                Element element = (Element)nodes.item(i);
                CallCenter callCenterToAdd = new CallCenter();
                callCenterToAdd.readIdFromXMLNode(element);
                callCenters.add(callCenterToAdd);                 
            }
        }
        catch (Exception e)
        {
            System.err.println("Error parsing XML from string! :" + e.getMessage());
            e.printStackTrace();
        }
        return callCenters;
    }

}
