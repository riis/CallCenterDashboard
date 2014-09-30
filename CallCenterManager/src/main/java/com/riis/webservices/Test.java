package com.riis.webservices;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
 
@Path("/test")
public class Test {
 
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/helloworld")
    public String getMessage()
    {
        return "{'test':'HelloWorld}";
    }
 
}
