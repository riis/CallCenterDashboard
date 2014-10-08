import static junit.framework.Assert.assertEquals;

import java.io.StringReader;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.riis.model.CallCenter;
import com.riis.model.CallCenterUpdateEvent;

import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.fail;


public class CallCenterTest
{
    DocumentBuilderFactory docBuilderFactory;
    DocumentBuilder docBuilder;
    Document doc;
    NodeList nodes;
    CallCenter callCenter;  
    static String CALL_CENTER_LIST_XML_RESULT = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>" +
            "<CallCenters xmlns=\"http://schema.broadsoft.com/xsi\">" +
            "    <serviceUserID>Premium@172.16.25.102</serviceUserID>" +
            "    <serviceUserID>PremiumTwo@172.16.25.102</serviceUserID>" +
            "</CallCenters>";
//    static String CALL_CENTER_LIST_REAL_XML_RESULT = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>" +
//    		"<CallCenters xmlns=\"http://schema.broadsoft.com/xsi\">" +
//    		"     <callCenter>" +
//    		"         <serviceUserID>CallCenterPrem@xdp.broadsoft.com</serviceUserID>" +
//    		"     </callCenter>" +
//    		"     <callCenter>" +
//    		"         <serviceUserID>callCenter3@xdp.broadsoft.com</serviceUserID>" +
//    		"     </callCenter>" +
//    		"     <callCenter>" +
//    		"         <serviceUserID>call_std2@xdp.broadsoft.com</serviceUserID>" +
//    		"     </callCenter>" +
//    		"</CallCenters>";
    static String CALL_CENTER_PROFILE_XML_RESULT = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>" + 
            "<ACDProfile xmlns=\"http://schema.broadsoft.com/xsi\">" +
            "   <serviceInstanceProfile>" +
            "       <name>Premium</name>" +
            "       <callingLineIdLastName>Premium</callingLineIdLastName>" +
            "       <callingLineIdFirstName>" +
            "           Premium" +
            "       </callingLineIdFirstName>" +
            "       <hiraganaLastName>Call Center</hiraganaLastName>" +
            "       <language>English</language>" +
            "       <timeZone>Asia/Calcutta</timeZone>" +
            "       <timeZoneDisplayName>" +
            "           (GMT+05:30) Asia/Calcutta" +
            "       </timeZoneDisplayName>" +
            "   </serviceInstanceProfile>" +
            "   <type>Premium</type>" +
            "   <policy>Regular</policy>" +
            "   <enableVideo>true</enableVideo>" +
            "   <queueLength>0</queueLength>" +
            "   <allowCallerToDialEscapeDigit>" +
            "       true" +
            "   </allowCallerToDialEscapeDigit>" +
            "   <escapeDigit>0</escapeDigit>" +
            "   <resetCallStatisticsUponEntryInQueue>" +
            "       false" +
            "   </resetCallStatisticsUponEntryInQueue>" +
            "   <allowAgentLogoff>true</allowAgentLogoff>" +
            "   <allowCallWaitingForAgents>" +
            "       true" +
            "   </allowCallWaitingForAgents>" +     
            "   <allowCallsToAgentsInWrapUp>" +
            "       true" +
            "   </allowCallsToAgentsInWrapUp>" +
            "   <overrideAgentWrapUpTime>false</overrideAgentWrapUpTime>" +
            "   <wrapUpSeconds>200</wrapUpSeconds>" +
            "   <forceDeliveryOfCalls>false</forceDeliveryOfCalls>" +
            "   <enableAutomaticStateChangeForAgents>" +
            "       False" +
            "   </enableAutomaticStateChangeForAgents>" +
            "   <agentStateAfterCall>Available</agentStateAfterCall>" +
            "   <externalPreferredAudioCodec>" +
            "       G729" +
            "   </externalPreferredAudioCodec>" +
            "   <internalPreferredAudioCodec>" +
            "       G729" +
            "   </internalPreferredAudioCodec>" +
            "   <playRingingWhenOfferingCall>" +
            "       true" +
            "   </playRingingWhenOfferingCall>" +
            "</ACDProfile>";

    static String CALL_CENTER_CALLS_XML_RESULT = 
            "<ACDQueue xmlns=\"http://schema.broadsoft.com/xsi\">" +
            "   <queueEntries>" +
            "       <queueEntry>" +
            "           <callId> </callId>" +
            "           <extTrackingId />" +
            "           <remoteParty>" +
            "           <name> </name>" +
            "           <address countryCode=\"0\"/>" +
            "           <userId> </userId>" +
            "           <userDN countryCode=\"0\"/>" +
            "           <privacy/>" +
            "           <callType> </callType>" +
            "           </remoteParty>" +
            "           <addTime> </addTime>" +
            "           <removeTime> </removeTime>" +
            "           <mandatoryEntrance/>" +
            "           <bounced/>" +
            "           <reordered/>" +
            "           <preservedWaitTime> </preservedWaitTime>" +
            "           <acdName> </acdName>" +
            "           <acdNumber countryCode=\"0\"/>" +
            "           <acdPriority>0-Highest</acdPriority>" +
            "           <addTimeInPriorityBucket/>" +
            "           <preservedWaitTimeInPriorityBucket>" +
            "           </preservedWaitTimeInPriorityBucket>" +
            "           <answeringUserId> </answeringUserId>" +
            "           <answeringCallId> </answeringCallId>" +
            "           <answeringNetworkCallId>" +  
            "           </answeringNetworkCallId>" +
            "       </queueEntry>" +
            "   </queueEntries>" +
            "</ACDQueue>";
    
    static String CALL_CENTER_SUBSCRBED_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
    		"<Subscription xmlns=\"http://schema.broadsoft.com/xsi\">" +
    		    "<subscriptionId>5ce0ca58-35f2-46e5-a60c-032a417137cf</subscriptionId>" +
    		    "<expires>3600</expires>" +
    		"</Subscription>";
    
    @Before
    public void setup()
    {
        try
        {
            docBuilderFactory = DocumentBuilderFactory.newInstance();
            docBuilder = docBuilderFactory.newDocumentBuilder();        
            callCenter = new CallCenter("@riis.com");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    
    @Test
    public void testReadIdFromXMLNode() throws Exception
    {      
        doc = docBuilder.parse(new InputSource(new StringReader(CALL_CENTER_LIST_XML_RESULT)));
//        doc = docBuilder.parse(new InputSource(new StringReader(CALL_CENTER_LIST_REAL_XML_RESULT)));
        doc.getDocumentElement().normalize();
        nodes = doc.getDocumentElement().getElementsByTagName("serviceUserID");              
        Node element = nodes.item(0);
        if (element != null)
        {
            callCenter.readIdFromXMLNode(element);            
        }
        else
        {
            fail("No Nodes found in list");
        }
        assertEquals("Premium@172.16.25.102",callCenter.getCallCenterId());
    }

    
    @Test
    public void testReadNameFromXMLString() throws Exception
    {      
        callCenter.readNameFromXMLString(CALL_CENTER_PROFILE_XML_RESULT);            
        assertEquals("Premium",callCenter.getCallCenterName());
    }
    
    
    @Test
    public void testQueueSizeFromXMLString() throws Exception
    {              
        callCenter.readQueueLengthFromXMLString(CALL_CENTER_CALLS_XML_RESULT);            
        assertEquals(1,callCenter.getQueueLength());
    }

    
    @Test
    public void testReadListFromXMLString() throws Exception
    {      
        List<CallCenter> result = callCenter.createListFromXMLString(CALL_CENTER_LIST_XML_RESULT);   
        assertEquals(2, result.size());
        assertEquals("Premium@172.16.25.102",result.get(0).getCallCenterId());
        assertEquals("PremiumTwo@172.16.25.102",result.get(1).getCallCenterId());
    }
    

    @Test
    public void testCallCenterParseSubscriptionXMLL() throws Exception
    {      
        callCenter.parseSubscriptionXMLString(CALL_CENTER_SUBSCRBED_XML);   
        assertEquals("5ce0ca58-35f2-46e5-a60c-032a417137cf", callCenter.getSubscriptionId());
    }
    
    @Test
    public void testUpdateFromEvent() throws Exception
    {      
        CallCenterUpdateEvent event = new CallCenterUpdateEvent();
        event.setNumCallsInQueue(10);
        callCenter.updateFromEvent(event);
        assertEquals(10, callCenter.getQueueLength());
    }

}
