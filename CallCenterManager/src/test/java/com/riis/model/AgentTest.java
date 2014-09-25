import static junit.framework.Assert.assertEquals;

import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.Before;
import org.junit.Test;

import com.riis.model.Agent;


public class AgentTest
{
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
    
    private Agent agent;

    @Before
    public void setup()
    {
        try
        {
            agent = new Agent();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    @Test
    public void testReadListFromXMLString() throws Exception
    {      
        List<Agent> result = agent.createListFromXMLString(AGENT_LIST_XML_RESULT);   
        assertEquals(3, result.size());
        
        assertEquals("gnolanUser2@xdp.broadsoft.com",result.get(0).getUserId());
        assertEquals("gnolan User2",result.get(0).getName());
        assertEquals("+1-2401003211",result.get(0).getPhoneNumber());
        assertEquals("3211",result.get(0).getExtension());        
        assertEquals("CallCenterPrem@xdp.broadsoft.com",result.get(0).getCallCenterId());
        
        assertEquals("gnolanUser3@xdp.broadsoft.com",result.get(1).getUserId());
        assertEquals("gnolan User3",result.get(1).getName());
        assertEquals("+1-2401003212",result.get(1).getPhoneNumber());
        assertEquals("3212",result.get(1).getExtension());        
        assertEquals("CallCenterPrem@xdp.broadsoft.com",result.get(1).getCallCenterId());

        assertEquals("gnolanUser2@xdp.broadsoft.com",result.get(2).getUserId());
        assertEquals("gnolan User2",result.get(2).getName());
        assertEquals("+1-2401003211",result.get(2).getPhoneNumber());
        assertEquals("3211",result.get(2).getExtension());        
        assertEquals("call_std2@xdp.broadsoft.com",result.get(2).getCallCenterId());

    }    
}
