package com.riis.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="callcenteragentsummary")
@XmlAccessorType(XmlAccessType.FIELD)
public class CallCenterAgentSummary
{
    @XmlElement(required=false)
    String callCenterName;
    @XmlElement(required=false)
    String callCenterId;
    @XmlElement(required=false)
    int agentsOnCallCount;
    @XmlElement(required=false)
    int agentsAvailableCount;
    @XmlElement(required=false)
    int agentsAwayCount;
    @XmlElement(required=false)
    int agentsSignedOutCount;

    
    public String getCallCenterName()
    {
        return callCenterName;
    }
    
    
    public void setCallCenterName(String callCenterName)
    {
        this.callCenterName = callCenterName;
    }
    
    
    public int getAgentsOnCallCount()
    {
        return agentsOnCallCount;
    }
    
    
    public void setAgentsOnCallCount(int agentsOnCallCount)
    {
        this.agentsOnCallCount = agentsOnCallCount;
    }
    
    
    public int getAgentsAvailableCount()
    {
        return agentsAvailableCount;
    }
    
    
    public void setAgentsAvailableCount(int agentsAvailableCount)
    {
        this.agentsAvailableCount = agentsAvailableCount;
    }
    
    
    public int getAgentsAwayCount()
    {
        return agentsAwayCount;
    }


    public void setAgentsAwayCount(int agentsAwayCount)
    {
        this.agentsAwayCount = agentsAwayCount;
    }


    public int getAgentsSignedOutCount()
    {
        return agentsSignedOutCount;
    }


    public void setAgentsSignedOutCount(int agentsSignedOutCount)
    {
        this.agentsSignedOutCount = agentsSignedOutCount;
    }


    public String getCallCenterId()
    {
        return callCenterId;
    }


    public void setCallCenterId(String callCenterId)
    {
        this.callCenterId = callCenterId;
    }

    
    public void resetCounts()
    {
        agentsOnCallCount = 0;
        agentsAvailableCount = 0;
        agentsAwayCount = 0;
        agentsSignedOutCount = 0;
    }
    
    
    public List<CallCenterAgentSummary> countAllAgents(List<Agent> agents, 
                    List<CallCenter>callCenters)
    {
        List<CallCenterAgentSummary> agentSummary = new ArrayList<CallCenterAgentSummary>();
        for (Agent agent : agents)
        {
            // find or add agentSummary for call center
            String currentCallCenterId = agent.getCallCenterId();
            CallCenterAgentSummary currentSummary= null;
            currentSummary = findExistingSummaryRecord(agentSummary, currentCallCenterId);
            if (currentSummary == null)
            {
                currentSummary = createNewSummaryRecord(callCenters, currentCallCenterId);
                agentSummary.add(currentSummary);
            }
            if (currentSummary != null)
            {
                updateCountForCurrentSummary(currentSummary, agent);
            }
        }
        return agentSummary;
    }


    private CallCenterAgentSummary findExistingSummaryRecord(
            List<CallCenterAgentSummary> agentSummary, String currentCallCenterId)
    {
        CallCenterAgentSummary currentSummary = null;
        for (CallCenterAgentSummary summary : agentSummary)
        {
            if (summary.getCallCenterId().equals(currentCallCenterId))
            {
                currentSummary = summary;
            }
        }
        return currentSummary;
    }


    private CallCenterAgentSummary createNewSummaryRecord(List<CallCenter> callCenters, 
            String currentCallCenterId)
    {
        CallCenterAgentSummary currentSummary = null;
        for (CallCenter currentCallCenter : callCenters)
        {
            if (currentCallCenter.getCallCenterId().equals(currentCallCenterId))
            {
                currentSummary = new CallCenterAgentSummary();
                currentSummary.setCallCenterId(currentCallCenterId);   
                currentSummary.setCallCenterName(currentCallCenter.getCallCenterName());
            }
        }
        return currentSummary;
    }  
    
    
    private void updateCountForCurrentSummary(CallCenterAgentSummary currentSummary, Agent agent)
    {
        if (Agent.AGENT_AVAILABLE_STATUS.equals(agent.getStatus()))
        {
            currentSummary.setAgentsAvailableCount(currentSummary.getAgentsAvailableCount() + 1);
        }
        if (Agent.AGENT_UNAVAILABLE_STATUS.equals(agent.getStatus()))
        {
            currentSummary.setAgentsAwayCount(currentSummary.getAgentsAwayCount() + 1);
        }
        if (Agent.AGENT_ONCALL_STATUS.equals(agent.getStatus()))
        {
            currentSummary.setAgentsOnCallCount(currentSummary.getAgentsOnCallCount() + 1);
        }
        if (Agent.AGENT_SIGNOUT_STATUS.equals(agent.getStatus()))
        {
            currentSummary.setAgentsSignedOutCount(currentSummary.getAgentsSignedOutCount() + 1);
        }
    }
}
