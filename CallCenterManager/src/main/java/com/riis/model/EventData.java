package com.riis.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "eventData")
@XmlAccessorType(XmlAccessType.FIELD)
public class EventData 
{
    String eventName;
    List<Call> call;
        
    public EventData() 
    {
        super();
    }

    
    public EventData(String eventName, List<Call> call) 
    {
        super();
        this.eventName = eventName;
        this.call = call;
    }


    public String getEventName() 
    {
        return eventName;
    }
    
    public void setEventName(String eventName) 
    {
        this.eventName = eventName;
    }
    
    public List<Call> getCall() 
    {
        return call;
    }
    
    public void setCall(List<Call> call) 
    {
        this.call = call;
    }
    
    public String toString()
    {
        StringBuffer buff = new StringBuffer("EventData:\n");
        buff.append("    eventName:" + eventName + "\n");
        if (call != null)
        {
            buff.append("    call:" + call.toString() + "\n");          
        }
        else
        {
            buff.append("    call:null\n");                     
        }
        return buff.toString();
    }

}
