package com.riis.controller;

import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;


import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMethod;

import com.riis.broadsoft.BroadsoftGateway;
import com.riis.model.Agent;
import com.riis.model.AgentUpdateEvent;
import com.riis.model.CallCenter;
import com.riis.model.CallCenterAgentSummary;
import com.riis.model.CallCenterUpdateEvent;
import com.riis.pusher.PusherGateway;

@Controller
public class WebserviceController
{
    static boolean initialized = false;
    
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
    //CallCruncher - test account
    private static final String HOST_NAME = "portal1.ipitimi.net";
    private static final String AUTHENTICATION_USERNAME = "ccruncherblue@nat.ipitimi.net";
    private static final String PASSWORD = "cD889n9HuvNK";
    private static final String SUPERVISOR_USERNAME = "3852363607@nat.ipitimi.net";
    // Road Runner
//    private static final String HOST_NAME = "xsp2.telesphere.com";
//    private static final String AUTHENTICATION_USERNAME = "Admin_Joseph@voip.tnltd.net";
//    private static final String PASSWORD = "velvet0121";
//    private static final String SUPERVISOR_USERNAME = "6237422277@voip.tnltd.net";

    
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
        if (!initialized)
        {
            initialize();
        }
        setupGatewayForAction(); 
        List<Agent> agentList = gateway.getAllAgents();
        return agentList;
    }


    @RequestMapping(value = "/webservices/callCenterList", method = RequestMethod.GET)
    public @ResponseBody List<CallCenter> getCallCenterList() throws IOException
    {
        if (!initialized)
        {
            initialize();
        }
        setupGatewayForAction(); 
        List<CallCenter> callCenterList = gateway.getAllCallCenters();
        return callCenterList;
    }

    
    @RequestMapping(value = "/webservices/callCenterAgentSummary", method = RequestMethod.GET)
    public @ResponseBody List<CallCenterAgentSummary> getCallCenterAgentSummary() throws IOException
    {
        if (!initialized)
        {
            initialize();
        }
        setupGatewayForAction(); 
        List<CallCenterAgentSummary> callCenterAgentSummaryList = gateway.getAllCallCenterAgentSummary();
        return callCenterAgentSummaryList;
    }
    

    @RequestMapping(value = "/webservices/callCenterSubscriptionCallback", method = RequestMethod.POST)
    @ResponseBody
    public String recieveCallCenterSubscriptionResponse(@RequestHeader HttpHeaders headers, @RequestBody String eventXML) throws IOException
    {        
        CallCenterUpdateEvent event = new CallCenterUpdateEvent();
        event.readEventFromXMLString(eventXML);
        String eventType = event.getEventType();
        CallCenter callCenter = gateway.findCallCenterBySubscriptionId(event.getSubscriptionId());
        if (CallCenterUpdateEvent.CALL_CENTER_STATE_EVENT.equals(eventType) && callCenter != null)
        {
            callCenter.updateFromEvent(event);
            PusherGateway pusher = new PusherGateway();
            pusher.pushCallCenterEventNotification(event);
        }
        if (CallCenterUpdateEvent.CALL_CENTER_SUBSCRIPTION_TERMINATION_EVENT.equals(eventType) 
                && callCenter != null)
        {
            System.out.println("Found subscription termination event for callCenter with Subscription Id : " + callCenter.getSubscriptionId() + " - re-subscribing");
            setupGatewayForEvent();
            gateway.subscribeCallCenter(callCenter);
        }
        else
        {
            // agent Call center event - do nothing
        }

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
    
    
    @RequestMapping(value = "/webservices/unsubscribeAllCallCenters", method = RequestMethod.GET)
    @ResponseBody
    public String unsubscribeAllCallCenters() throws IOException
    {
        setupGatewayForEvent(); 
        gateway.unsubscribeAllCallCenters();
        return "{'unsubscribed':'AllCallCenters'}";
    }
    
    
    @RequestMapping(value = "/webservices/agentSubscriptionCallback", method = RequestMethod.POST)
    @ResponseBody
    public String recieveAgentSubscriptionResponse(@RequestHeader HttpHeaders headers,@RequestBody String eventXML) throws IOException
    {        
        AgentUpdateEvent event = new AgentUpdateEvent();
        event.readEventFromXMLString(eventXML);
        String eventType = event.getEventType();
        Agent agent = gateway.findAgentBySubscriptionId(event.getSubscriptionId());
        if (AgentUpdateEvent.AGENT_STATE_EVENT.equals(eventType) && agent != null)
        {
            agent.updateFromEvent(event);
            // find out if agent is on a call
            if (event.getState() != null && (! (Agent.AGENT_SIGNOUT_STATUS.equals(event.getState()) )))
            {
                setupGatewayForAction(); 
                gateway.getAgentCalls(agent);
                String status = agent.getStatus();
                // agent may  updated to On-Call - so update event
                event.setState(agent.getStatus());
            }
            // update all agents to keep them in sync across call centers
            List<Agent> agents = gateway.findAgentsByAgentId(agent.getAgentId());
            for (Agent currentAgent : agents)
            {
                currentAgent.setStatus(event.getState());
            }
            System.out.println("Agent status set to " + agent.getStatus());
            PusherGateway pusher = new PusherGateway();
            pusher.pushAgentEventNotification(event);
        }
        if (AgentUpdateEvent.AGENT_SUBSCRIPTION_TERMINATION_EVENT.equals(eventType) && agent != null)
        {
            System.out.println("Found subscription termination event for agent with Subscription Id : " + agent.getSubscriptionId() + " - re-subscribing");
            setupGatewayForEvent();
            gateway.subscribeAgent(agent);
        }
        if (AgentUpdateEvent.AGENT_SUBSCRIPTION_EVENT.equals(eventType))
        {
            // No need to do anything here ???
            System.out.println("Found agent subscription event in callback");
        }
        else
        {
            // agent unknown event - do nothing
        }
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


    @RequestMapping(value = "/webservices/unsubscribeAllAgents", method = RequestMethod.GET)
    @ResponseBody
    public String unsubscribeAllAgents() throws IOException
    {
        setupGatewayForEvent(); 
        gateway.unsubscribeAllAgents();
        return "{'unsubscribed':'AllAgents'}";
    }


    @RequestMapping(value = "/webservices/initialize", method = RequestMethod.GET)
    @ResponseBody
    public String initialize() throws IOException
    {
        if( initialized == false)
        {
            setupGatewayForAction(); 
            gateway.getAllAgents();
            gateway.getAllCallCenters();
            setupGatewayForEvent(); 
            gateway.subscribeAllCallCenters();
            gateway.subscribeAllAgents();
            initialized = true;
        }
        return "{'initialize':'true'}";
    }

    @RequestMapping(value = "/webservices/uninitialize", method = RequestMethod.GET)
    @ResponseBody
    public String uninitialize() throws IOException
    {
        if( initialized == true)
        {
            unsubscribeAllAgents();
            unsubscribeAllCallCenters();
            gateway.clearCache();
            initialized = false;
        }
        return "{'initialize':'false'}";
    }

    @PostConstruct
    public void init()
    {
        System.out.println("Called @PostConstruct init method");
        try
        {
            initialize();
        }
        catch (IOException e)
        {
            System.out.println("Exception caught during @PostConstruct init methos");
            e.printStackTrace();
        }
    }
}
