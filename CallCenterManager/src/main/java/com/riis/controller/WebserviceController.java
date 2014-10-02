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
import com.riis.model.CallCenter;
import com.riis.model.CallCenterAgentSummary;
import com.riis.pusher.PusherGateway;

@Controller
public class WebserviceController
{
    private BroadsoftGateway gateway;
    
    @RequestMapping(value = "/webservices/test", method = RequestMethod.GET)
    @ResponseBody
    public String getMessage()
    {
        PusherGateway pusher = new PusherGateway();
        pusher.pushTestNotification();
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


    @RequestMapping(value = "/webservices/callCenterList", method = RequestMethod.GET)
    public @ResponseBody List<CallCenter> getCallCenterList() throws IOException
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
        List<CallCenter> callCenterList = gateway.getAllCallCenters();
        return callCenterList;
    }

    
    @RequestMapping(value = "/webservices/callCenterAgentSummary", method = RequestMethod.GET)
    public @ResponseBody List<CallCenterAgentSummary> getCallCenterAgentSummary() throws IOException
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
        List<CallCenterAgentSummary> callCenterAgentSummaryList = gateway.getAllCallCenterAgentSummary();
        return callCenterAgentSummaryList;
    }
    

    @RequestMapping(value = "webservices/callCenterSubscriptionCallback", method = RequestMethod.POST)
    public void recieveCallCenterSubscriptionResponse(String XML) throws IOException
    {
        System.out.println("RecievedResponseString: "+ XML);
    }
    
    
    @RequestMapping(value = "/webservices/subscribeAllCallCenters", method = RequestMethod.GET)
    public String subscribeAllCallCenters() throws IOException
    {
        if (gateway == null)
        {
            gateway = new BroadsoftGateway();
            
        }
        gateway.subscribeAllCallCenters();
        return "{'test':'HelloWorld}";
    }
}
