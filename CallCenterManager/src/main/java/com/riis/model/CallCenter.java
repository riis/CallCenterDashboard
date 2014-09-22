package com.riis.model;

public class CallCenter
{
    private String callCenterId;
    private String callCenterName;
    private int queueLength;
    
    public String getCallCenterId()
    {
        return callCenterId;
    }
    
    
    public void setCallCenterId(String callCenterId)
    {
        this.callCenterId = callCenterId;
    }
    
    
    public String getCallCenterName()
    {
        return callCenterName;
    }
    
    
    public void setCallCenterName(String callCenterName)
    {
        this.callCenterName = callCenterName;
    }
    
    
    public int getQueueLength()
    {
        return queueLength;
    }
    
    
    public void setQueueLength(int queueLength)
    {
        this.queueLength = queueLength;
    }
}
