import static junit.framework.Assert.assertNull;
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


public class ModelTest
{
    public Model testModel;
    
    @Before
    public void setUp()
    {
        testModel = Model.getInstance();
        
        List<Agent> agents = new ArrayList<Agent>();
        
        Agent agentOne = new Agent("@riis.com");
        agentOne.setName("AgentOne");
        agentOne.setAgentId("ag1");
        agentOne.setCallCenterId("CCOne");
        agentOne.setSubscriptionId("subscription1");
        agentOne.setStatus(Agent.AGENT_ONCALL_STATUS);
        agents.add(agentOne);
        
        Agent agentTwo = new Agent("@riis.com");
        agentTwo.setName("AgentTwo");
        agentTwo.setAgentId("ag2");
        agentTwo.setCallCenterId("CCOne");
        agentTwo.setSubscriptionId("subscription2");
        agentTwo.setStatus(Agent.AGENT_AVAILABLE_STATUS);
        agents.add(agentTwo);
        
        Agent agentThree = new Agent("@riis.com");
        agentThree.setName("AgentThree");
        agentThree.setAgentId("ag1");
        agentThree.setCallCenterId("CCTwo");
        agentThree.setSubscriptionId("subscription3");
        agentThree.setStatus(Agent.AGENT_AVAILABLE_STATUS);
        agents.add(agentThree);
        
        List<CallCenter> callCenters = new ArrayList<CallCenter>();
        
        CallCenter callCenterOne = new CallCenter("@riis.com");
        callCenterOne.setCallCenterName("CallCenterOne");
        callCenterOne.setCallCenterId("CCOne");
        callCenterOne.setSubscriptionId("CallCenterSub1");
        callCenters.add(callCenterOne);
        
        CallCenter callCenterTwo = new CallCenter("@riis.com");
        callCenterTwo.setCallCenterName("CallCenterTwo");
        callCenterTwo.setCallCenterId("CCTwo");
        callCenterTwo.setSubscriptionId("CallCenterSub2");
        callCenters.add(callCenterTwo);        
          
        testModel.setAgents(agents);
        testModel.setCallCenters(callCenters);
    }

    
    @Test
    public void testGetAgentWithSubscriptionId()
    {
        Agent foundAgent = testModel.findAgentBySubscriptionId("subscription1");
        assertEquals("AgentOne", foundAgent.getName());           
        foundAgent = testModel.findAgentBySubscriptionId("subscription3");
        assertEquals("AgentThree", foundAgent.getName());           
    }

    
    @Test
    public void testGetAgentsWithAgnetId()
    {
        List<Agent> foundAgents = testModel.findAgentsByAgentId("ag1@riis.com");
        assertEquals(2, foundAgents.size());
        Agent foundAgent = foundAgents.get(0);
        assertEquals("AgentOne", foundAgent.getName());           
        foundAgent = foundAgents.get(1);
        assertEquals("AgentThree", foundAgent.getName());           
    }

    @Test
    public void testGetUniqueAgentIds()
    {
        List<String> uniqueAgentIds = testModel.getUniqueAgentIds();
        assertEquals(2, uniqueAgentIds.size());
        assertEquals("ag1@riis.com", uniqueAgentIds.get(0));           
        assertEquals("ag2@riis.com", uniqueAgentIds.get(1));           
    }

    @Test
    public void testPriorotizeStatus()
    {
        assertEquals(Agent.AGENT_ONCALL_STATUS, 
                testModel.prioritizeStatus(Agent.AGENT_ONCALL_STATUS, Agent.AGENT_AVAILABLE_STATUS));
        assertEquals(Agent.AGENT_ONCALL_STATUS, 
                testModel.prioritizeStatus(Agent.AGENT_AVAILABLE_STATUS, Agent.AGENT_ONCALL_STATUS));
        assertEquals(Agent.AGENT_ONCALL_STATUS, 
                testModel.prioritizeStatus(Agent.AGENT_ONCALL_STATUS, Agent.AGENT_UNAVAILABLE_STATUS));
        assertEquals(Agent.AGENT_ONCALL_STATUS, 
                testModel.prioritizeStatus(Agent.AGENT_ONCALL_STATUS, Agent.AGENT_SIGNOUT_STATUS));
        assertEquals(Agent.AGENT_UNAVAILABLE_STATUS, 
                testModel.prioritizeStatus(Agent.AGENT_UNAVAILABLE_STATUS, Agent.AGENT_AVAILABLE_STATUS));
        assertEquals(Agent.AGENT_AVAILABLE_STATUS, 
                testModel.prioritizeStatus(Agent.AGENT_AVAILABLE_STATUS, Agent.AGENT_SIGNOUT_STATUS));
        assertEquals(Agent.AGENT_ONCALL_STATUS, 
                testModel.prioritizeStatus(Agent.AGENT_ONCALL_STATUS, null));
        assertEquals(Agent.AGENT_UNAVAILABLE_STATUS, 
                testModel.prioritizeStatus(null, null));
    }

    @Test
    public void testApplySameStatusToAgentsInAllCallCenters()
    {
        testModel.applySameStatusToAgentsInAllCallCenters();
        Agent testAgent = testModel.findAgentBySubscriptionId("subscription1");
        assertEquals(Agent.AGENT_ONCALL_STATUS, testAgent.getStatus());           
        testAgent = testModel.findAgentBySubscriptionId("subscription3");
        assertEquals(Agent.AGENT_ONCALL_STATUS, testAgent.getStatus());                   
    }

}
