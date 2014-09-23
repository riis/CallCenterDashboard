package com.riis.model;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;


import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.InputSource;

public class CallCenter extends AbstractXMLParser implements XMLParserContract
{
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
    
    
    public void readFromXMLNode(Node element)
    {
        try
        {
            Element eElement = (Element) element;
            callCenterId = readFromNodeWithPath(eElement, "serviceUserId");            
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
//        <?xml version="1.0" encoding="ISO-8859-1"?>
//        <CallCenters xmlns="http://schema.broadsoft.com/xsi�>    <serviceUserID>Premium@172.16.25.102</serviceUserID>    <serviceUserID>PremiumTwo@172.16.25.102</serviceUserID>
//        </CallCenters>
        try
        {
            doc = docBuilder.parse(new InputSource(new StringReader(broadsoftXML)));
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
                callCenterToAdd.readFromXMLNode(element);
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
