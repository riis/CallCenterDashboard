package com.riis.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "remoteParty")
@XmlAccessorType(XmlAccessType.FIELD)
public class RemoteParty 
{
    String name;
    String address;
    String userId;
    String callType;
    
    public RemoteParty() 
    {
        super();
    }

    
    public RemoteParty(String name, String address, String userId,
            String callType) 
    {
        super();
        this.name = name;
        this.address = address;
        this.userId = userId;
        this.callType = callType;
    }


    public String getName() 
    {
        return name;
    }
    
    public void setName(String name) 
    {
        this.name = name;
    }
    
    public String getAddress() 
    {
        return address;
    }
    
    public void setAddress(String address) 
    {
        this.address = address;
    }
    
    public String getUserId() 
    {
        return userId;
    }
    
    public void setUserId(String userId) 
    {
        this.userId = userId;
    }
    
    public String getCallType() 
    {
        return callType;
    }
    
    public void setCallType(String callType) 
    {
        this.callType = callType;
    }
    
    public String toString()
    {
        StringBuffer buff = new StringBuffer("RemoteParty:\n");
        buff.append("    name:" + name + "\n");
        buff.append("    address:" + address + "\n");
        buff.append("    userId:" + userId + "\n");
        buff.append("    callType:" + callType + "\n");
        return buff.toString();
    }
}
