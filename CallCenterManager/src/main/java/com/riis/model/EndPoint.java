package com.riis.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "endPoint")
@XmlAccessorType(XmlAccessType.FIELD)
public class EndPoint 
{
    String addressOfRecord;

    public EndPoint() 
    {
        super();
    }

    public EndPoint(String addressOfRecord) 
    {
        super();
        this.addressOfRecord = addressOfRecord;
    }

    public String getAddressOfRecord() 
    {
        return addressOfRecord;
    }

    public void setAddressOfRecord(String addressOfRecord) 
    {
        this.addressOfRecord = addressOfRecord;
    }
    
    public String toString()
    {
        StringBuffer buff = new StringBuffer("EndPoint:\n");
        buff.append("    addressOfRecord:" + addressOfRecord + "\n");
        return buff.toString();
    }
}
