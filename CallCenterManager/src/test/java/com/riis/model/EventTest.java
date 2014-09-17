package com.riis.model;

import org.junit.Test;
import static junit.framework.Assert.assertEquals;

public class EventTest
{
    @Test
    public void testEventConstruction()
    {
        Event event = new Event();
        assertEquals(-1, event.getEventId());
    }
}
