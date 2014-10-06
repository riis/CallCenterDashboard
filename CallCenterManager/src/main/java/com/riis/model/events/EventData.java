package com.riis.model.events;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.riis.model.Call;


@XmlRootElement(name = "eventData")
@XmlAccessorType(XmlAccessType.FIELD)
public class EventData implements Serializable
{
    private static final long serialVersionUID = -8461126789610017352L;

    @XmlElement(required=false)
    List<AgentStateInfo> agentStateInfo;
        
    public EventData() 
    {
        super();
    }

    
    public EventData(List<AgentStateInfo> agentStateInfo) 
    {
        super();
        this.agentStateInfo = agentStateInfo;
    }


    public List<AgentStateInfo> getAgentStateInfo() 
    {
        return agentStateInfo;
    }
    
    public void setAgentStateInfo(List<AgentStateInfo> agentStateInfo) 
    {
        this.agentStateInfo = agentStateInfo;
    }
    
    public String toString()
    {
        StringBuffer buff = new StringBuffer("EventData:\n");
        if (agentStateInfo != null)
        {
            buff.append("    agentStateInfo:" + agentStateInfo.toString() + "\n");          
        }
        else
        {
            buff.append("    agentStateInfo:null\n");                     
        }
        return buff.toString();
    }

}
