package com.riis.model;

import static junit.framework.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.Before;
import org.junit.Test;

import com.riis.model.Agent;
import com.riis.model.CallCenter;
import com.riis.model.CallCenterAgentSummary;

public class CallCenterAgentSummaryTest
{
    List<Agent> agentList;
    List<CallCenter> callCenterList;
    CallCenterAgentSummary summary;
    
    @Before
    public void setup()
    {
        try
        {
            summary = new CallCenterAgentSummary();
            // Create Call Centers
            callCenterList = new ArrayList<CallCenter>();
            CallCenter callCenterOne = new CallCenter("@riis.com");
            callCenterOne.setCallCenterId("cc1");
            callCenterOne.setCallCenterName("callCenterOne");
            callCenterList.add(callCenterOne);
            CallCenter callCenterTwo = new CallCenter("@riis.com");
            callCenterTwo.setCallCenterId("cc2");
            callCenterTwo.setCallCenterName("callCenterTwo");
            callCenterList.add(callCenterTwo);
            
            // Create Agents
            agentList = new ArrayList<Agent>();
            Agent agentOne = new Agent("@riis.com");
            agentOne.setAgentId("agent1");
            agentOne.setCallCenterId("cc1");
            agentOne.setStatus(Agent.AGENT_AVAILABLE_STATUS);
            agentList.add(agentOne);
            Agent agentTwo = new Agent("@riis.com");
            agentTwo.setAgentId("agent2");
            agentTwo.setCallCenterId("cc1");
            agentTwo.setStatus(Agent.AGENT_ONCALL_STATUS);
            agentList.add(agentTwo);
            Agent agentThree = new Agent("@riis.com");
            agentThree.setAgentId("agent3");
            agentThree.setCallCenterId("cc1");
            agentThree.setStatus(Agent.AGENT_SIGNOUT_STATUS);
            agentList.add(agentThree);
            Agent agentFour = new Agent("@riis.com");
            agentFour.setAgentId("agent4");
            agentFour.setCallCenterId("cc2");
            agentFour.setStatus(Agent.AGENT_UNAVAILABLE_STATUS);
            agentList.add(agentFour);
            Agent agentFive = new Agent("@riis.com");
            agentFive.setAgentId("agent5");
            agentFive.setCallCenterId("cc2");
            agentFive.setStatus(Agent.AGENT_UNAVAILABLE_STATUS);
            agentList.add(agentFive);            
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    
    @Test
    public void testCountAllAgents() throws Exception
    {
        
        List<CallCenterAgentSummary> summaryList = summary.countAllAgents(agentList, callCenterList);
        assertEquals(2, summaryList.size());
        //check counts are correct for first call center
        CallCenterAgentSummary cc1 = summaryList.get(0);
        assertEquals("cc1@riis.com", cc1.getCallCenterId());
        assertEquals("callCenterOne", cc1.getCallCenterName());
        assertEquals(1, cc1.getAgentsAvailableCount());
        assertEquals(1, cc1.getAgentsOnCallCount());
        assertEquals(1, cc1.getAgentsSignedOutCount());
        assertEquals(0, cc1.getAgentsAwayCount());
        //check counts are correct for second call center
        CallCenterAgentSummary cc2 = summaryList.get(1);
        assertEquals("cc2@riis.com", cc2.getCallCenterId());
        assertEquals("callCenterTwo", cc2.getCallCenterName());
        assertEquals(0, cc2.getAgentsAvailableCount());
        assertEquals(0, cc2.getAgentsOnCallCount());
        assertEquals(0, cc2.getAgentsSignedOutCount());
        assertEquals(2, cc2.getAgentsAwayCount());        
    }
}
