package com.riis.model;

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
    }

    public List<Agent> getAgents()
    {
        return agents;
    }

    public void setAgents(List<Agent> agents)
    {
        this.agents = agents;
    }
    
    public void clearCache()
    {
        this.agents = null;
        this.callCenters = null;
        
    }
}
