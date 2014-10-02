package com.riis.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "call")
@XmlAccessorType(XmlAccessType.FIELD)
public class Call 
{
    String callId;
    String extTrackingId;
    String personality;
    String callState;
    List<RemoteParty> remoteParty;
    String addressOfRecord;
    List<EndPoint> endPoint;
    String appearance;
    String startTime;
        
    public Call() 
    {
        super();
    }

    
    public Call(String callId, String extTrackingId, String personality,
            String callState, List<RemoteParty> remoteParty,
            String addressOfRecord, List<EndPoint> endPoint, String appearance,
            String startTime) 
    {
        super();
        this.callId = callId;
        this.extTrackingId = extTrackingId;
        this.personality = personality;
        this.callState = callState;
        this.remoteParty = remoteParty;
        this.addressOfRecord = addressOfRecord;
        this.endPoint = endPoint;
        this.appearance = appearance;
        this.startTime = startTime;
    }


    public String getCallId() 
    {
        return callId;
    }
    
    public void setCallId(String callId) 
    {
        this.callId = callId;
    }
    
    public String getExtTrackingId() 
    {
        return extTrackingId;
    }
    
    public void setExtTrackingId(String extTrackingId) 
    {
        this.extTrackingId = extTrackingId;
    }
    
    public String getPersonality() 
    {
        return personality;
    }
    
    public void setPersonality(String personality) 
    {
        this.personality = personality;
    }
    
    public String getCallState() 
    {
        return callState;
    }
    
    public void setCallState(String callState) 
    {
        this.callState = callState;
    }
    
    public List<RemoteParty> getRemoteParty() 
    {
        return remoteParty;
    }
    
    public void setRemoteParty(List<RemoteParty> remoteParty) 
    {
        this.remoteParty = remoteParty;
    }
    
    public String getAddressOfRecord() 
    {
        return addressOfRecord;
    }
    
    public void setAddressOfRecord(String addressOfRecord) 
    {
        this.addressOfRecord = addressOfRecord;
    }
    
    public List<EndPoint> getEndPoint() 
    {
        return endPoint;
    }
    
    public void setEndPoint(List<EndPoint> endPoint) 
    {
        this.endPoint = endPoint;
    }
    
    public String getAppearance() 
    {
        return appearance;
    }
    
    public void setAppearance(String appearance) 
    {
        this.appearance = appearance;
    }
    
    public String getStartTime() 
    {
        return startTime;
    }
    
    public void setStatTime(String statTime) 
    {
        this.startTime = statTime;
    }
    
    public String toString()
    {
        StringBuffer buff = new StringBuffer("Call:\n");
        buff.append("    callId:" + callId + "\n");
        buff.append("    extTrackingId:" + extTrackingId + "\n");
        buff.append("    personality:" + personality + "\n");
        buff.append("    callState:" + callState + "\n");
        if (remoteParty != null)
        {
            buff.append("    remoteParty:" + remoteParty.toString() + "\n");            
        }
        else
        {
            buff.append("    remoteParty:NULL\n");                      
        }
        buff.append("    addressOfRecord:" + addressOfRecord + "\n");
        if (endPoint != null)
        {
            buff.append("    endPoint:" + endPoint.toString() + "\n");          
        }
        else
        {
            buff.append("    endPoint:NULL\n");                     
        }
        buff.append("    appearance:" + appearance + "\n");
        buff.append("    startTime:" + startTime + "\n");
        return buff.toString();
    }   
}
