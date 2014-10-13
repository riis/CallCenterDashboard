import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;

import java.io.StringReader;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.riis.model.Agent;
import com.riis.model.AgentUpdateEvent;
import com.riis.model.CallCenterUpdateEvent;


public class AgentTest
{
    DocumentBuilderFactory docBuilderFactory;
    DocumentBuilder docBuilder;
    Document doc;
    NodeList nodes;

    private static final String AGENT_LIST_XML_RESULT = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>" +
    		"<CallCenterAgents xmlns=\"http://schema.broadsoft.com/xsi\">" +
    		"     <callCenter>" +
    		"         <serviceUserID>CallCenterPrem@xdp.broadsoft.com</serviceUserID>" +
    		"         <agentList>" +
    		"             <userDetails>" +
    		"                 <userId>gnolanUser2@xdp.broadsoft.com</userId>" +
    		"                 <firstName>gnolan</firstName>" +
    		"                 <lastName>User2</lastName>" +
    		"                 <hiranganaLastName>User2</hiranganaLastName>" +
    		"                 <hiranganaFirstName>gnolan</hiranganaFirstName>" +
    		"                 <number>+1-2401003211</number>" +
    		"                 <extension>3211</extension>" +
    		"             </userDetails>" +
    		"             <userDetails>" +
    		"                 <userId>gnolanUser3@xdp.broadsoft.com</userId>" +
    		"                 <firstName>gnolan</firstName>" +
    		"                 <lastName>User3</lastName>" +
    		"                 <hiranganaLastName>User3</hiranganaLastName>" +
    		"                 <hiranganaFirstName>gnolan</hiranganaFirstName>" +
    		"                 <number>+1-2401003212</number>" +
    		"                 <extension>3212</extension>" +
    		"             </userDetails>" +
    		"         </agentList>" +
    		"     </callCenter>" +
    		"     <callCenter>" +
    		"         <serviceUserID>callCenter3@xdp.broadsoft.com</serviceUserID>" +
    		"         <agentList/>" +
    		"     </callCenter>" +
    		"     <callCenter>" +
    		"         <serviceUserID>call_std2@xdp.broadsoft.com</serviceUserID>" +
    		"         <agentList>" +
    		"             <userDetails>" +
    		"                 <userId>gnolanUser2@xdp.broadsoft.com</userId>" +
    		"                 <firstName>gnolan</firstName>" +
    		"                 <lastName>User2</lastName>" +
    		"                 <hiranganaLastName>User2</hiranganaLastName>" +
    		"                 <hiranganaFirstName>gnolan</hiranganaFirstName>" +
    		"                 <number>+1-2401003211</number>" +
    		"                 <extension>3211</extension>" +
    		"             </userDetails>" +
    		"         </agentList>" +
    		"     </callCenter>" +
    		"</CallCenterAgents>";
    
    private static String AGENT_REFRESH_XML = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>" +
    		"<CallCenter xmlns=\"http://schema.broadsoft.com/xsi\">" +
    		"     <agentACDState>Available</agentACDState>" +
    		"     <useDefaultGuardTimer>true</useDefaultGuardTimer>" +
    		"     <enableGuardTimer>false</enableGuardTimer>" +
    		"     <guardTimerSeconds>5</guardTimerSeconds>" +
    		"     <useSystemDefaultUnavailableSettings>true</useSystemDefaultUnavailableSettings>" +
    		"     <forceAgentUnavailableOnDNDActivation>false</forceAgentUnavailableOnDNDActivation>" +
    		"     <forceUnavailableOnPersonalCalls>false</forceUnavailableOnPersonalCalls>" +
    		"     <forceAgentUnavailableOnBouncedCallLimit>false</forceAgentUnavailableOnBouncedCallLimit>" +
    		"     <numberConsecutiveBouncedCallsToForceAgentUnavailable>3</numberConsecutiveBouncedCallsToForceAgentUnavailable>" +
    		"     <makeOutgoingCallsAsCallCenter>false</makeOutgoingCallsAsCallCenter>" +
    		"     <callCenterList>" +
    		"         <callCenterDetails>" +
    		"             <serviceUserId>CallCenterPrem@xdp.broadsoft.com</serviceUserId>" +
    		"             <available>false</available>" +
    		"             <phoneNumber>2401003214</phoneNumber>" +
    		"             <extension>3214</extension>" +
    		"             <isLogOffAllowed>true</isLogOffAllowed>" +
    		"         </callCenterDetails>" +
    		"         <callCenterDetails>" +
    		"             <serviceUserId>call_std2@xdp.broadsoft.com</serviceUserId>" +
    		"             <available>false</available>" +
    		"             <phoneNumber>2401003213</phoneNumber>" +
    		"             <extension>3213</extension>" +
    		"             <isLogOffAllowed>true</isLogOffAllowed>" +
    		"         </callCenterDetails>" +
    		"     </callCenterList>" +
    		"</CallCenter>";
    
    private static String AGENT_SUBSCRIBED_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
    		"<Subscription xmlns=\"http://schema.broadsoft.com/xsi\">" +
    		    "<subscriptionId>e71fc027-5fea-4928-99ba-6cf793e69f18</subscriptionId>" +
    		    "<expires>3600</expires>" +
    		"</Subscription>";

    
    private static String AGENT_CALLS_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
    		"<Calls xmlns=\"http://schema.broadsoft.com/xsi\">" +
    		    "<call inConference=\"false\">" +
    		        "<callId>callhalf-722:0</callId>" +
    		        "<uri>/v2.0/user/negi001@172.16.25.102/calls/callhalf-722:0</uri>" +
    		    "</call>" +
    		"</Calls>";
    
    private static String AGENT_CALLS_NO_CALL_XML = "<Calls xmlns=\"http://schema.broadsoft.com/xsi\"/>";

    private Agent agent;

    @Before
    public void setup()
    {
        try
        {
            docBuilderFactory = DocumentBuilderFactory.newInstance();
            docBuilder = docBuilderFactory.newDocumentBuilder();        
            agent = new Agent("@riis.com");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    @Test
    public void testReadIdFromXMLNode() throws Exception
    {      
        doc = docBuilder.parse(new InputSource(new StringReader(AGENT_LIST_XML_RESULT)));
        doc.getDocumentElement().normalize();
        nodes = doc.getDocumentElement().getElementsByTagName("userDetails");              
        Node element = nodes.item(0);
        if (element != null)
        {
          agent.readIdFromXMLNode(element);            
        }
        else
        {
            fail("No Nodes found in list");
        }        
        assertEquals("gnolanUser2@xdp.broadsoft.com",agent.getAgentId());
        assertEquals("gnolan User2",agent.getName());
        assertEquals("+1-2401003211",agent.getPhoneNumber());
        assertEquals("3211",agent.getExtension());        
    }    

    
    @Test
    public void testReadListFromXMLString() throws Exception
    {      
        List<Agent> result = agent.createListFromXMLString(AGENT_LIST_XML_RESULT);   
        assertEquals(3, result.size());
        
        assertEquals("gnolanUser2@xdp.broadsoft.com",result.get(0).getAgentId());
        assertEquals("gnolan User2",result.get(0).getName());
        assertEquals("+1-2401003211",result.get(0).getPhoneNumber());
        assertEquals("3211",result.get(0).getExtension());        
        assertEquals("CallCenterPrem@xdp.broadsoft.com",result.get(0).getCallCenterId());
        
        assertEquals("gnolanUser3@xdp.broadsoft.com",result.get(1).getAgentId());
        assertEquals("gnolan User3",result.get(1).getName());
        assertEquals("+1-2401003212",result.get(1).getPhoneNumber());
        assertEquals("3212",result.get(1).getExtension());        
        assertEquals("CallCenterPrem@xdp.broadsoft.com",result.get(1).getCallCenterId());

        assertEquals("gnolanUser2@xdp.broadsoft.com",result.get(2).getAgentId());
        assertEquals("gnolan User2",result.get(2).getName());
        assertEquals("+1-2401003211",result.get(2).getPhoneNumber());
        assertEquals("3211",result.get(2).getExtension());        
        assertEquals("call_std2@xdp.broadsoft.com",result.get(2).getCallCenterId());
    }    


    @Test
    public void testReadStatusXMLString() throws Exception
    {      
        agent.readStatusFromXMLString(AGENT_REFRESH_XML);   
        assertEquals("Available", agent.getStatus());
    }    


    @Test
    public void testAgentParseSubscriptionXML() throws Exception
    {      
        agent.parseSubscriptionXMLString(AGENT_SUBSCRIBED_XML);   
        assertEquals("e71fc027-5fea-4928-99ba-6cf793e69f18", agent.getSubscriptionId());
    }


    @Test
    public void testUpdateFromEvent()
    {      
        AgentUpdateEvent event = new AgentUpdateEvent();
        event.setState("TEST_STATE");
        agent.updateFromEvent(event);
        assertEquals("TEST_STATE", agent.getStatus());
    }

    @Test
    public void testParseCallsFromXML()
    {      
        agent.setStatus("TEST_STATE");
        agent.parseCallsFromXML(AGENT_CALLS_XML);
        assertEquals(Agent.AGENT_ONCALL_STATUS, agent.getStatus());
    }

    @Test
    public void testParseCallsNoCallFromXML()
    {      
        agent.setStatus("TEST_STATE");
        agent.parseCallsFromXML(AGENT_CALLS_NO_CALL_XML);
        assertEquals("TEST_STATE", agent.getStatus());
    }
}
