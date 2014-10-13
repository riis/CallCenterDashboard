package com.riis.model;

import java.util.ArrayList;
import java.util.List;

// Singleton class to store CallCenter and Agent info
public class Model
{
    private static Model instance = null;
    
    private boolean initialized = false;
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
    
    public boolean isInitialized()
    {
        return initialized;
    }

    public void setInitialized(boolean initialized)
    {
        this.initialized = initialized;
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

}
