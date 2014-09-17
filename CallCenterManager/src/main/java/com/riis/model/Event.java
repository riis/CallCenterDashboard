package com.riis.model;

public class Event
{
    int eventId = 0;
    
    public Event()
    {
        this.eventId = -1;
    }
    
    public int getEventId()
    {
        return eventId;
    }

    public void setEventId(int eventId)
    {
        this.eventId = eventId;
    }    
}
