package com.riis.controller;

import java.io.IOException;
import java.util.List;


import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMethod;

import com.riis.broadsoft.BroadsoftGateway;
import com.riis.model.Agent;
import com.riis.model.CallCenter;
import com.riis.model.CallCenterAgentSummary;
import com.riis.model.Event;
import com.riis.pusher.PusherGateway;

@Controller
public class WebserviceController
{
    private static final String PROTOCOL = "http";
//    private static final String HOST_NAME = "xsp2.xdp.broadsoft.com";
    private static final String ACTION_PATH = "com.broadsoft.xsi-actions/v2.0";    
    private static final String EVENT_PATH = "com.broadsoft.xsi-events/v2.0";   
//    private static final String AUTHENTICATION_USERNAME = "gnolanAdmin1@xdp.broadsoft.com";
//    private static final String PASSWORD = "welcome1";
//    private static final String SUPERVISOR_USERNAME = "gnolanUser1@xdp.broadsoft.com";
    //CallCruncher
//    private static final String HOST_NAME = "portal1.ipitimi.net";
//    private static final String AUTHENTICATION_USERNAME = "ccruncherblue@nat.ipitimi.net";
//    private static final String PASSWORD = "cD889n9HuvNK";
//    private static final String SUPERVISOR_USERNAME = "100008@nat.ipitimi.net";
    // Road Runner
    private static final String HOST_NAME = "xsp2.telesphere.com";
    private static final String AUTHENTICATION_USERNAME = "Admin_Joseph@voip.tnltd.net";
    private static final String PASSWORD = "velvet0121";
    private static final String SUPERVISOR_USERNAME = "6237422277@voip.tnltd.net";

    
    private BroadsoftGateway gateway;
    
    private void setupGatewayForAction()
    {
        if (gateway == null)
        {
            gateway = new BroadsoftGateway();            
        }
        gateway.setProtocol(PROTOCOL);
        gateway.setHostName(HOST_NAME);
        gateway.setActionPath(ACTION_PATH);
        gateway.setAuthenticationUsername(AUTHENTICATION_USERNAME);
        gateway.setPassword(PASSWORD);
        gateway.setSupervisorUsername(SUPERVISOR_USERNAME);
    }


    private void setupGatewayForEvent()
    {
        if (gateway == null)
        {
            gateway = new BroadsoftGateway();
            
        }
        gateway.setProtocol(PROTOCOL);
        gateway.setHostName(HOST_NAME);
        gateway.setActionPath(EVENT_PATH);
        gateway.setAuthenticationUsername(AUTHENTICATION_USERNAME);
        gateway.setPassword(PASSWORD);
        gateway.setSupervisorUsername(SUPERVISOR_USERNAME);
    }
    
    
    @RequestMapping(value = "/webservices/test", method = RequestMethod.GET)
    @ResponseBody
    public String getMessage()
    {
        PusherGateway pusher = new PusherGateway();
        pusher.pushTestNotification();
        return "{'test':'HelloWorld}";
    }


    @RequestMapping(value = "/webservices/clearCache", method = RequestMethod.GET)
    @ResponseBody
    public String clearCache()
    {
        gateway.clearCache();
        return "{'clearCache':'Done'}";
    }


    @RequestMapping(value = "/webservices/agentList", method = RequestMethod.GET)
    public @ResponseBody List<Agent> getAgentList() throws IOException
    {
        setupGatewayForAction(); 
        List<Agent> agentList = gateway.getAllAgents();
        return agentList;
    }


    @RequestMapping(value = "/webservices/callCenterList", method = RequestMethod.GET)
    public @ResponseBody List<CallCenter> getCallCenterList() throws IOException
    {
        setupGatewayForAction(); 
        List<CallCenter> callCenterList = gateway.getAllCallCenters();
        return callCenterList;
    }

    
    @RequestMapping(value = "/webservices/callCenterAgentSummary", method = RequestMethod.GET)
    public @ResponseBody List<CallCenterAgentSummary> getCallCenterAgentSummary() throws IOException
    {
        setupGatewayForAction(); 
        List<CallCenterAgentSummary> callCenterAgentSummaryList = gateway.getAllCallCenterAgentSummary();
        return callCenterAgentSummaryList;
    }
    

    @RequestMapping(value = "/webservices/callCenterSubscriptionCallback", method = RequestMethod.POST)
    @ResponseBody
//    public String recieveCallCenterSubscriptionResponse(@RequestHeader HttpHeaders headers, Event event) throws IOException
    public String recieveCallCenterSubscriptionResponse(@RequestParam String event) throws IOException
    {        
        System.out.println("callCenterSubscriptionCallback called");
        System.out.println("Event String... :" + event);
        PusherGateway pusher = new PusherGateway();
//        pusher.pushCallCenterEventNotification(event);
        return "OK";
    }
    
    
    @RequestMapping(value = "/webservices/subscribeAllCallCenters", method = RequestMethod.GET)
    @ResponseBody
    public String subscribeAllCallCenters() throws IOException
    {
        setupGatewayForEvent(); 
        gateway.subscribeAllCallCenters();
        return "{'subscribed':'AllCallCenters'}";
    }
    
    
    @RequestMapping(value = "/webservices/agentSubscriptionCallback", method = RequestMethod.POST)
    @ResponseBody
//    public String recieveAgentSubscriptionResponse(@RequestHeader HttpHeaders headers, Event event) throws IOException
    public String recieveAgentSubscriptionResponse(@RequestParam String event) throws IOException
    {        
        System.out.println("agentSubscriptionCallback called");
        System.out.println("Event String... :" + event);
        PusherGateway pusher = new PusherGateway();
//        pusher.pushAgentEventNotification(event);
        return "OK";
    }
    
    
    @RequestMapping(value = "/webservices/subscribeAllAgents", method = RequestMethod.GET)
    @ResponseBody
    public String subscribeAllAgents() throws IOException
    {
        setupGatewayForEvent(); 
        gateway.subscribeAllAgents();
        return "{'subscribed':'AllAgents'}";
    }
}
