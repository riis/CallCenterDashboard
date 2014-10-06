package com.riis.model.events;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="agentStateInfo")
@XmlAccessorType(XmlAccessType.FIELD)
public class AgentStateInfo implements Serializable
{
    private static final long serialVersionUID = -4753124477179510295L;

    @XmlElement(required=true)
    String state;
    @XmlElement(required=true)
    Date stateTimestamp;
    @XmlElement(required=true)
    Date signInTimestamp;
    @XmlElement(required=true)
    Long totalAvailableTime;
    @XmlElement(required=true)
    Long averageWrapUpTime;
    
    public AgentStateInfo() 
    {
        super();
    }

    public AgentStateInfo(String state, Date stateTimestamp, Date signInTimestamp,
            Long totalAvailableTime, Long averageWrapUpTime) 
    {
        super();
        this.state = state;
        this.stateTimestamp = stateTimestamp;
        this.signInTimestamp = signInTimestamp;
        this.totalAvailableTime = totalAvailableTime;
        this.averageWrapUpTime = averageWrapUpTime;
    }
    
    public String getState() 
    {
        return state;
    }
    
    public void setState(String state) 
    {
        this.state = state;
    }
    
    public Date getStateTimestamp() 
    {
        return stateTimestamp;
    }
    
    public void setStateTimestamp(Date stateTimestamp) 
    {
        this.stateTimestamp = stateTimestamp;
    }
    
    public Date getSignInTimestamp() 
    {
        return signInTimestamp;
    }
    
    public void setSignInTimestamp(Date signInTimestamp) 
    {
        this.signInTimestamp = signInTimestamp;
    }
    
    public Long getTotalAvailableTime() 
    {
        return totalAvailableTime;
    }
    
    public void setTotalAvailableTime(Long totalAvailableTime) 
    {
        this.totalAvailableTime = totalAvailableTime;
    }
    
    public Long getAverageWrapUpTime() 
    {
        return averageWrapUpTime;
    }
    
    public void setAverageWrapUpTime(Long averageWrapUpTime) 
    {
        this.averageWrapUpTime = averageWrapUpTime;
    }
    
    
    public String toString()
    {
        StringBuffer buff = new StringBuffer("AgentStateInfo:\n");
        buff.append("    state:" + state + "\n");
        buff.append("    stateTimestamp:" + stateTimestamp + "\n");
        buff.append("    signInTimestamp:" + signInTimestamp + "\n");
        buff.append("    totalAvailableTime:" + totalAvailableTime + "\n");
        buff.append("    averageWrapUpTime:" + averageWrapUpTime + "\n");
        return buff.toString();
    }

}
