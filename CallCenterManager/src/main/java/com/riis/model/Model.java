package com.riis.model;

import java.util.ArrayList;
import java.util.List;

// Singleton class to store CallCenter and Agent info
public class Model
{
    private static Model instance = null;
    
    private List<CallCenter> callCenters;
    private List<Agent> agents;
    
    private Model()
    {
    }
    
    public static Model getInstance()
    {
        if (instance == null)
        {
            instance = new Model();
        }
        return instance;
    }
    
    public List<CallCenter> getCallCenters()
    {
        return callCenters;
    }

    public void setCallCenters(List<CallCenter> callCenters)
    {
        this.callCenters = callCenters;
        if (callCenters != null)
        {
            removeCallCentersWithNoAgents();
        }
    }

    public List<Agent> getAgents()
    {
        return agents;
    }

    public void setAgents(List<Agent> agents)
    {
        this.agents = agents;
        if (callCenters != null)
        {
            removeCallCentersWithNoAgents();
        }
        applySameStatusToAgentsInAllCallCenters();
    }
    
    public void clearCache()
    {
        this.agents = null;
        this.callCenters = null;   
    }
    
    private void removeCallCentersWithNoAgents()
    {
        if (agents != null && agents.size()>0 && callCenters != null && callCenters.size()>0)
        {
            List<CallCenter> callCentersToRemove = new ArrayList<CallCenter>();
            for (CallCenter callcenter : callCenters)
            {
                boolean found = false;
                for (Agent agent : agents)
                {
                    if (callcenter.getCallCenterId().equals(agent.getCallCenterId()))
                    {
                        found = true;
                        break;
                    }
                }
                if (!found)
                {
                    callCentersToRemove.add(callcenter);
                }
            }
            callCenters.removeAll(callCentersToRemove);
        }
    }

    
    public CallCenter findCallCenterBySubscriptionId(String subscriptionId)
    {
        for(CallCenter callCenter : callCenters)
        {
            if (subscriptionId.equals(callCenter.getSubscriptionId()))
            {
                return callCenter;
            }
        }
        return null;
    }

    
    public Agent findAgentBySubscriptionId(String subscriptionId)
    {
        for(Agent agent : agents)
        {
            if (subscriptionId.equals(agent.getSubscriptionId()))
            {
                return agent;
            }
        }
        return null;
    }


    public List<Agent> findAgentsByAgentId(String agentId)
    {
        List<Agent> foundAgents = new ArrayList<Agent>();
        if (agentId != null)
        {
            for(Agent agent : agents)
            {
                if (agentId.equals(agent.getAgentId()))
                {
                    foundAgents.add(agent);
                }
            }            
        }
        return foundAgents;
    }
    

    public String prioritizeStatus(String statusOne, String statusTwo)
    {
        if(statusOne == Agent.AGENT_ONCALL_STATUS || statusTwo == Agent.AGENT_ONCALL_STATUS)
        {
            return Agent.AGENT_ONCALL_STATUS;
        }
        if(statusOne == Agent.AGENT_UNAVAILABLE_STATUS || statusTwo == Agent.AGENT_UNAVAILABLE_STATUS)
        {
            return Agent.AGENT_UNAVAILABLE_STATUS;
        }
        if(statusOne == Agent.AGENT_AVAILABLE_STATUS || statusTwo == Agent.AGENT_AVAILABLE_STATUS)
        {
            return Agent.AGENT_AVAILABLE_STATUS;
        }
        if(statusOne == Agent.AGENT_SIGNOUT_STATUS || statusTwo == Agent.AGENT_SIGNOUT_STATUS)
        {
            return Agent.AGENT_SIGNOUT_STATUS;
        }
        return Agent.AGENT_UNAVAILABLE_STATUS;
    }

    
    public void applySameStatusToAgentsInAllCallCenters()
    {
        List<String> agentIds = getUniqueAgentIds();
        for (String currentId : agentIds)
        {
            String priorityStatus = null;
            boolean applyUpdate = false;

            List<Agent> agentsWithId = findAgentsByAgentId(currentId);
            for (Agent currentAgent : agentsWithId)
            {
                if (priorityStatus == null)
                {
                    // first status so nothing to update
                    priorityStatus = currentAgent.getStatus();
                    System.out.println("First setting of priorityStatus : " + priorityStatus);
                }
                else if (priorityStatus != currentAgent.getStatus())
                {
                    applyUpdate = true;
                    priorityStatus = prioritizeStatus(priorityStatus, currentAgent.getStatus());  
                    System.out.println("found agent with another status,  priorityStatus : " + priorityStatus);

                }
                else
                {
                    //all agents have same status, so do nothing
                }
            }
            if (applyUpdate)
            {
                for (Agent currentAgent : agentsWithId)
                {
                    currentAgent.setStatus(priorityStatus);
                    System.out.println("applying priorityStatus : " + priorityStatus + " to agent " + currentAgent.getName());

                }
            }
        }
    }
    
    public List<String> getUniqueAgentIds()
    {
        List<String> foundIds = new ArrayList<String>();
        for(Agent agent : agents)
        {
            if (agent.getAgentId() != null && ( ! foundIds.contains(agent.getAgentId())))
            {
                foundIds.add(agent.getAgentId());
            }
        }
        return foundIds;
    }

}
