package com.riis.model.events;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="xsi:httpContact")
@XmlAccessorType(XmlAccessType.FIELD)
public class HTTPContact implements Serializable
{
    private static final long serialVersionUID = 2524656448673086063L;

    @XmlElement(name="xsi:uri", required=true)
    String uri;

    public HTTPContact() 
    {
        super();
    }

    public HTTPContact(String uri) 
    {
        super();
        this.uri = uri;
    }

    public String getUri()
    {
        return uri;
    }

    public void setUri(String uri)
    {
        this.uri = uri;
    }
    
    public String toString()
    {
        StringBuffer buff = new StringBuffer("HTTPContact:\n");
        buff.append("    uri:" + uri + "\n");
        return buff.toString();
    }

}
