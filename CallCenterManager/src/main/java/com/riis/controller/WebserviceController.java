package com.riis.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMethod;

import com.riis.broadsoft.BroadsoftGateway;
import com.riis.model.Agent;

@Controller
public class WebserviceController
{
    private BroadsoftGateway gateway;
    
    @RequestMapping(value = "/webservices/test", method = RequestMethod.GET)
    @ResponseBody
    public String getMessage()
    {
        return "{'test':'HelloWorld}";
    }


    @RequestMapping(value = "/webservices/agentList", method = RequestMethod.GET)
    public @ResponseBody List<Agent> getAgentList() throws IOException
    {
        if (gateway == null)
        {
            gateway = new BroadsoftGateway();
            
        }
        gateway.setProtocol("http");
        gateway.setHostName("xsp2.xdp.broadsoft.com");
        gateway.setActionPath("com.broadsoft.xsi-actions/v2.0");
        gateway.setAuthenticationUsername("gnolanAdmin1@xdp.broadsoft.com");
        gateway.setPassword("welcome1"); 
        List<Agent> agentList = gateway.getAllAgents();
        return agentList;
    }


}
