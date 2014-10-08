import static junit.framework.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.riis.broadsoft.BroadsoftGateway;
import com.riis.model.Agent;
import com.riis.model.CallCenter;
import com.riis.model.Model;

import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertNotNull;


public class BroadsoftGatewayTest
{    
    @Before
    public void setUp()
    {
        Model model = Model.getInstance();
        
        List<Agent> agents = new ArrayList<Agent>();
        
        Agent agentOne = new Agent("@riis.com");
        agentOne.setName("AgentOne");
        agentOne.setSubscriptionId("subscription1");
        agents.add(agentOne);
        
        Agent agentTwo = new Agent("@riis.com");
        agentTwo.setName("AgentTwo");
        agentTwo.setSubscriptionId("subscription2");
        agents.add(agentTwo);
        
        Agent agentThree = new Agent("@riis.com");
        agentThree.setName("AgentThree");
        agentThree.setSubscriptionId("subscription3");
        agents.add(agentThree);
        
        List<CallCenter> callCenters = new ArrayList<CallCenter>();
        
        CallCenter callCenterOne = new CallCenter("@riis.com");
        callCenterOne.setCallCenterName("CallCenterOne");
        callCenterOne.setSubscriptionId("CallCenterSub1");
        callCenters.add(callCenterOne);
        
        CallCenter callCenterTwo = new CallCenter("@riis.com");
        callCenterTwo.setCallCenterName("CallCenterTwo");
        callCenterTwo.setSubscriptionId("CallCenterSub2");
        callCenters.add(callCenterTwo);        
          
        model.setAgents(agents);
        model.setCallCenters(callCenters);
    }

    @Test
    public void testCallWithNoConfiguration()
    {
        try
        {
            BroadsoftGateway gateway = new BroadsoftGateway();
            String response = gateway.makeRequest("profile", BroadsoftGateway.REQUEST_METHOD_GET, null);
            assertNull(response);           
        }
        catch (IOException e)
        {
            System.err.print("Exception Thrown in BroadsoftGateWayTest: " + e.getMessage());
        }
     }

    
    @Test
    public void testCallPartialConfiguration()
    {
//        try
//        {
//            BroadsoftGateway gateway = new BroadsoftGateway();
//            gateway.setHostName("xsp2.xdp.broadsoft.com");
//            gateway.setAuthenticationUsername("gnolanAdmin1@xdp.broadsoft.com");
//            gateway.setPassword("welcome1");            
//            String response = gateway.makeRequest("directories/Agents", BroadsoftGateway.REQUEST_METHOD_GET, null);
//            assertNull(response);           
//        }
//        catch (IOException e)
//        {
//            System.err.print("Exception Thrown in BroadsoftGateWayTest: " + e.getMessage());
//        }
     }


    @Test
    public void testCallFullConfigurationGetAllCallCenters()
    {
//        try
//        {
//            BroadsoftGateway gateway = new BroadsoftGateway();
//            gateway.setProtocol("http");
//            gateway.setHostName("xsp2.xdp.broadsoft.com");
//            gateway.setActionPath("com.broadsoft.xsi-actions/v2.0");
//            gateway.setAuthenticationUsername("gnolanAdmin1@xdp.broadsoft.com");
//            gateway.setPassword("welcome1"); 
//            String response = gateway.makeRequest("user/gnolanUser1@xdp.broadsoft.com/directories/CallCenters?user=Supervisor", 
//                    BroadsoftGateway.REQUEST_METHOD_GET, null);
//            assertNotNull(response);
//        }
//        catch (IOException e)
//        {
//            System.err.print("Exception Thrown in BroadsoftGateWayTest: " + e.getMessage());
//        }
     }

    @Test
    public void testCallFullConfigurationCallCenterProfile()
    {
//        try
//        {
//            BroadsoftGateway gateway = new BroadsoftGateway();
//            gateway.setProtocol("http");
//            gateway.setHostName("xsp2.xdp.broadsoft.com");
//            gateway.setActionPath("com.broadsoft.xsi-actions/v2.0");
//            gateway.setAuthenticationUsername("gnolanAdmin1@xdp.broadsoft.com");
//            gateway.setPassword("welcome1"); 
//            String callCenterProfile = gateway.makeRequest("callcenter/CallCenterPrem@xdp.broadsoft.com/profile",
//                    BroadsoftGateway.REQUEST_METHOD_GET, null);            
//            assertNotNull(callCenterProfile);
//        }
//        catch (IOException e)
//        {
//            System.err.print("Exception Thrown in BroadsoftGateWayTest: " + e.getMessage());
//        }
     }
    
    @Test
    public void testCallFullConfigurationCallCenterQueues()
    {
//        try
//        {
//            BroadsoftGateway gateway = new BroadsoftGateway();
//            gateway.setProtocol("http");
//            gateway.setHostName("xsp2.xdp.broadsoft.com");
//            gateway.setActionPath("com.broadsoft.xsi-actions/v2.0");
//            gateway.setAuthenticationUsername("gnolanAdmin1@xdp.broadsoft.com");
//            gateway.setPassword("welcome1"); 
//            String callCenterCalls = gateway.makeRequest("callcenter/CallCenterPrem@xdp.broadsoft.com/calls",
//                    BroadsoftGateway.REQUEST_METHOD_GET, null); 
//            assertNotNull(callCenterCalls);
//        }
//        catch (IOException e)
//        {
//            System.err.print("Exception Thrown in BroadsoftGateWayTest: " + e.getMessage());
//        }
     }

    @Test
    public void testGetAllCallCenters()
    {
//        try
//        {
//            // Need to eventually inject this test with Mock Objects
//            BroadsoftGateway gateway = new BroadsoftGateway();
//            gateway.setProtocol("http");
//            gateway.setHostName("xsp2.xdp.broadsoft.com");
//            gateway.setActionPath("com.broadsoft.xsi-actions/v2.0");
//            gateway.setAuthenticationUsername("gnolanAdmin1@xdp.broadsoft.com");
//            gateway.setPassword("welcome1"); 
//            gateway.setSupervisorUsername("gnolanUser1@xdp.broadsoft.com");
//            List<CallCenter> allCallCenters = gateway.getAllCallCenters();
//            assertEquals(3, allCallCenters.size());
//            CallCenter premCallCenter = allCallCenters.get(0);
//            assertEquals("CallCenterPrem@xdp.broadsoft.com", premCallCenter.getCallCenterId());
//            assertEquals("CallCenterPrem", premCallCenter.getCallCenterName());
//            assertEquals(0, premCallCenter.getQueueLength());       
//        }
//        catch (IOException e)
//        {
//            System.err.print("Exception Thrown in BroadsoftGateWayTest: " + e.getMessage());
//        }
    }


    @Test
    public void testGetAllAgents()
    {
//        try
//        {
//            // Need to eventually inject this test with Mock Objects
//            BroadsoftGateway gateway = new BroadsoftGateway();
//            gateway.setProtocol("http");
//            gateway.setHostName("xsp2.xdp.broadsoft.com");
//            gateway.setActionPath("com.broadsoft.xsi-actions/v2.0");
//            gateway.setAuthenticationUsername("gnolanAdmin1@xdp.broadsoft.com");
//            gateway.setPassword("welcome1"); 
//            gateway.setSupervisorUsername("gnolanUser1@xdp.broadsoft.com");
//            List<Agent> allAgents = gateway.getAllAgents();
//            assertEquals(2, allAgents.size());
//            Agent gnolanUser2 = allAgents.get(0);
//            assertEquals("gnolanUser2@xdp.broadsoft.com",gnolanUser2.getAgentId());
////            assertEquals("Available",gnolanUser2.getStatus());            
//            assertEquals("gnolan User2",gnolanUser2.getName());
//            assertEquals("+1-2401003211",gnolanUser2.getPhoneNumber());
//            assertEquals("3211",gnolanUser2.getExtension());        
//            assertEquals("CallCenterPrem@xdp.broadsoft.com",gnolanUser2.getCallCenterId());
//
//            assertNotNull(allAgents);
//        }
//        catch (IOException e)
//        {
//            System.err.print("Exception Thrown in BroadsoftGateWayTest: " + e.getMessage());
//        }
    }

//    @Test
//    public void testFindAgentBySubscription()
//    {
//        BroadsoftGateway gateway = new BroadsoftGateway();
//        Agent response = gateway.findAgentBySubscriptionId("Subscription2");
//        assertNotNull(response);
//        assertEquals("AgentTwo", response.getName());
//    }

//    @Test
//    public void testFindCallCenterBySubscription()
//    {
//        BroadsoftGateway gateway = new BroadsoftGateway();
//        CallCenter response = gateway.findCallCenterBySubscriptionId("CallCenterSub2");
//        assertNotNull(response);
//        assertEquals("CallCenterTwo", response.getCallCenterName());
//    }
}
