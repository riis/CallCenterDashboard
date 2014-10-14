package com.riis.broadsoft;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import javax.xml.bind.DatatypeConverter;

import com.riis.model.Agent;
import com.riis.model.CallCenter;
import com.riis.model.CallCenterAgentSummary;
import com.riis.model.Model;

public class BroadsoftGateway
{
    public static final String REQUEST_METHOD_GET = "GET";
    public static final String REQUEST_METHOD_POST = "POST";
    public static final String REQUEST_METHOD_DELETE = "DELETE";
    private static final String PROTOCOL_SEPARATOR = "://";
    private static final String PATH_SEPARATOR = "/";
    private static final String AUTH_TOKEN_SEPARATOR = ":";
    private static final String AUTH_PREFIX_BASIC = "Basic ";
    private static final String PROPERTY_AUTHORIZATION = "Authorization";
    private static final String CALLBACK_CALLCENTER_ADDRESS = "ec2-54-205-41-129.compute-1.amazonaws.com:8080/" +
    		"CallCenterManager-CallCruncher/webservices/callCenterSubscriptionCallback";
    private static final String CALLBACK_AGENT_ADDRESS = "ec2-54-205-41-129.compute-1.amazonaws.com:8080/" +
            "CallCenterManager-CallCruncher/webservices/agentSubscriptionCallback";
    private String protocol;
    private String actionPath;
    private String hostName;
    private String authenticationUsername;
    private String password;
    private String sessionCookie;
    private String supervisorUsername;
    private String domain;
    
    private Model model = Model.getInstance();
    
    public String getProtocol()
    {
        return protocol;
    }

    
    public void setProtocol(String protocol)
    {
        this.protocol = protocol;
    }

    
    public String getActionPath()
    {
        return actionPath;
    }

    
    public void setActionPath(String actionPath)
    {
        this.actionPath = actionPath;
    }

    
    public String getHostName()
    {
        return hostName;
    }

    
    public void setHostName(String hostName)
    {
        this.hostName = hostName;
    }

    
    public String getAuthenticationUsername()
    {
        return authenticationUsername;
    }


    public void setAuthenticationUsername(String authenticationUsername)
    {
        domain = authenticationUsername.substring(authenticationUsername.indexOf('@'));
        this.authenticationUsername = authenticationUsername;
    }


    public String getPassword()
    {
        return password;
    }

    
    public void setPassword(String password)
    {
        this.password = password;
    }

    
    public String getSessionCookie()
    {
        return sessionCookie;
    }

    
    public void setSessionCookie(HttpURLConnection urlConnection)
    {
        String headerName = null;
        for (int i=1; (headerName = urlConnection.getHeaderFieldKey(i)) != null; i++)
        {
            if (headerName.equals("Set-Cookie"))
            {               
                sessionCookie = urlConnection.getHeaderField(i);
            }
        }
    }
    

    public String getSupervisorUsername()
    {
        return supervisorUsername;
    }


    public void setSupervisorUsername(String supervisorUsername)
    {
        this.supervisorUsername = supervisorUsername;
    }


    public String makeRequest(String action, String requestMethod, String body) throws IOException
    {
        String responseXML = null;
        if (checkConfiguration())
        {
            URL url = new URL(protocol + PROTOCOL_SEPARATOR 
                    + hostName + PATH_SEPARATOR 
                    + actionPath + PATH_SEPARATOR 
                    + action );
            
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod(requestMethod);
            setAuthorization(urlConnection);
            if (getSessionCookie() != null)
            {
                urlConnection.setRequestProperty("Cookie", sessionCookie);
            }

            if (body != null)
            {
                urlConnection.setRequestProperty("Content-Type", "application/xml");                
                urlConnection.setRequestProperty("Content-Length", "" + 
                        Integer.toString(body.getBytes().length));       
                urlConnection.setRequestProperty("Content-Language", "en-US");  
                    
                urlConnection.setUseCaches (false);
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);
                
               //Send request
               DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream ());
               wr.writeBytes (body);
               wr.flush ();
               wr.close ();
            }

            setSessionCookie(urlConnection);
            
            responseXML = getResponseXML(urlConnection);
        }
        return responseXML;
    }
    
    public List<CallCenter> getAllCallCenters() throws IOException
    {
        if (model.getCallCenters() == null || model.getCallCenters().isEmpty())
        {
            String CallCenterXML =  makeRequest("user/" + getSupervisorUsername() + "/directories/CallCenters?user=Supervisor", 
                    REQUEST_METHOD_GET, null);
            List<CallCenter> allCallCenters = new CallCenter(domain).createListFromXMLString(CallCenterXML);
            for(CallCenter callCenter : allCallCenters)
            {
                String callCenterProfile = makeRequest("callcenter/" + callCenter.getCallCenterId() + "/profile",
                        REQUEST_METHOD_GET, null);            
                callCenter.readNameFromXMLString(callCenterProfile);
                String callCenterCalls = makeRequest("callcenter/" + callCenter.getCallCenterId() + "/calls",
                        REQUEST_METHOD_GET, null); 
                callCenter.readQueueLengthFromXMLString(callCenterCalls);
            } 
            model.setCallCenters(allCallCenters);
        }
        return model.getCallCenters();
    }
    
    public List<Agent> getAllAgents() throws IOException
    {
        if (model.getAgents() == null)
        {
            String agentXML =  makeRequest("user/" + getSupervisorUsername() + "/directories/Agents",
                    REQUEST_METHOD_GET, null);
            List<Agent> allAgents = new Agent(domain).createListFromXMLString(agentXML);
            for(Agent agent : allAgents)
            {
//                String callCenterProfile = makeRequest("callcenter/" + callCenter.getCallCenterId() + "/profile");            
//                callCenter.readNameFromXMLString(callCenterProfile);
//                String callCenterCalls = makeRequest("callcenter/" + callCenter.getCallCenterId() + "/calls"); 
//                callCenter.readQueueLengthFromXMLString(callCenterCalls);
                refreshAgentStatus(agent);
                if (agent.getStatus() != null && (! (Agent.AGENT_SIGNOUT_STATUS.equals(agent.getStatus()) )))
                {
                    getAgentCalls(agent);                    
                }
            }
            model.setAgents(allAgents);
        }
        return model.getAgents();
    }
    

    public void getAgentCalls(Agent agent) throws IOException
    {
        String callsXML =  makeRequest("user/" + agent.getAgentId() + "/calls",
                REQUEST_METHOD_GET, null);
        agent.parseCallsFromXML(callsXML);        
    }

    
    public List<CallCenterAgentSummary> getAllCallCenterAgentSummary() throws IOException
    {
        List<Agent> allAgents = getAllAgents();
        List<CallCenter> allCallCenters = getAllCallCenters();
        CallCenterAgentSummary summary = new CallCenterAgentSummary();
        List<CallCenterAgentSummary> summaryList = summary.countAllAgents(allAgents, allCallCenters);
        return summaryList;
    }

    
    public void refreshAgentStatus(Agent agent) throws IOException
    {
        String refreshXML =  makeRequest("user/" + agent.getAgentId() + "/services/CallCenter",
                REQUEST_METHOD_GET, null);
        agent.readStatusFromXMLString(refreshXML);
    }
    
    
    public void subscribeAllCallCenters() throws IOException
    {
        List<CallCenter> allCallCenters = getAllCallCenters();
        for (CallCenter callCenter : allCallCenters)
        {
            System.out.println("Subscribing to call center " + callCenter.getCallCenterId());
            subscribeCallCenter(callCenter);
        }
    }
    
    public void subscribeCallCenter(CallCenter callCenter) throws IOException
    {
        String callCenterId = callCenter.getCallCenterId();
        String body = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
        		"<Subscription xmlns=\"http://schema.broadsoft.com/xsi\">" +
        		    "<subscriberId>" + authenticationUsername + "</subscriberId>" +
        		    "<targetIdType>User</targetIdType>" +
        		    "<targetId>" + callCenterId + "</targetId>" +
        		    "<event>Call Center Monitoring</event>" +
        		    "<httpContact> " +
        		        "<uri>" + CALLBACK_CALLCENTER_ADDRESS + "</uri>" +
        		    "</httpContact>" +
        		    "<applicationId>CallCenterDashboard</applicationId>" +
        		"</Subscription>";
        
        String requestString = "user/" + callCenterId;
        String callCenterSubscriptionXML =  makeRequest(requestString, 
                REQUEST_METHOD_POST, body);
        System.out.println("CallCenter Subscription Response = " + callCenterSubscriptionXML);
        callCenter.parseSubscriptionXMLString(callCenterSubscriptionXML);
    }

    
    public void unsubscribeAllCallCenters() throws IOException
    {
        List<CallCenter> allCallCenters = getAllCallCenters();
        for (CallCenter callCenter : allCallCenters)
        {
            unsubscribeCallCenter(callCenter);
        }
    }

    
    public void unsubscribeCallCenter(CallCenter callCenter) throws IOException
    {
        //http(s)://<host:port>/com.broadsoft.xsi- events/v2.0/subscription/<subscriptionid>
        String subscriptionId = callCenter.getSubscriptionId();
        if (subscriptionId != null)
        {
            // must set subscription id to null to stop it from 
            // re-subscribing once we receive a subscription termination event
            callCenter.setSubscriptionId(null);
            String unsubscribeResponseXML =  makeRequest("subscription/" + subscriptionId,
                    REQUEST_METHOD_DELETE, null);
            System.out.println("Unsubscribed CallCenter: " + callCenter.getCallCenterName() + " Response = " + unsubscribeResponseXML);          
        }
        else
        {
            System.out.println("CallCenter without subscriptionId :-(");
        }
        //agent.readStatusFromXMLString(refreshXML);
    }
    
    
    public void subscribeAllAgents() throws IOException
    {
        List<Agent> allAgents = getAllAgents();
        for (Agent agent : allAgents)
        {
            subscribeAgent(agent);
        }
    }

    
    public void subscribeAgent(Agent agent) throws IOException
    {
        String agentId = agent.getAgentId();
        String body = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                "<Subscription xmlns=\"http://schema.broadsoft.com/xsi\">" +
                    "<subscriberId>" + authenticationUsername + "</subscriberId>" +
                    "<targetIdType>User</targetIdType>" +
                    "<targetId>" + agentId + "</targetId>" +
                    "<event>Call Center Agent</event>" +
                    "<httpContact> " +
                        "<uri>" + CALLBACK_AGENT_ADDRESS + "</uri>" +
                    "</httpContact>" +
                    "<applicationId>CallCenterDashboard</applicationId>" +
                "</Subscription>";
        
        String requestString = "user/" + agentId;
        String agentSubscriptionXML =  makeRequest(requestString, 
                REQUEST_METHOD_POST, body);
        System.out.println("agentSubscriptionXML = " + agentSubscriptionXML);
        agent.parseSubscriptionXMLString(agentSubscriptionXML);
    }


    public void unsubscribeAllAgents() throws IOException
    {
        List<Agent> allAgents = getAllAgents();
        for (Agent agent : allAgents)
        {
            unsubscribeAgent(agent);
        }
    }

    
    public void unsubscribeAgent(Agent agent) throws IOException
    {
        //http(s)://<host:port>/com.broadsoft.xsi- events/v2.0/subscription/<subscriptionid>
        String subscriptionId = agent.getSubscriptionId();
        if (subscriptionId != null)
        {
            // must set subscription id to null to stop it from 
            // re-subscribing once we receive a subscription termination event
            agent.setSubscriptionId(null);
            String unsubscribeResponseXML =  makeRequest("subscription/" + subscriptionId,
                    REQUEST_METHOD_DELETE, null);
            System.out.println("Unsubscribed Agent: " + agent.getName() + " Response = " + unsubscribeResponseXML);          
        }
        else
        {
            System.out.println("Agent without subscriptionId :-(");
        }
        //agent.readStatusFromXMLString(refreshXML);
    }
    
    
    private boolean checkConfiguration()
    {
        if (protocol == null || actionPath == null || hostName == null 
                || authenticationUsername == null || password == null)
        {
            return false;
        }
        return true;
    }
    
    private void  setAuthorization(HttpURLConnection urlConnection)
    {
        if (authenticationUsername == null || password == null || urlConnection == null)
        {
            return;
        }
        // create the password for authentication
        StringBuilder pwd = new StringBuilder();
        pwd.append(authenticationUsername);
        pwd.append(AUTH_TOKEN_SEPARATOR);
        pwd.append(password);
        
        StringBuilder authentication = new StringBuilder();
        authentication.append(AUTH_PREFIX_BASIC);
        authentication.append( DatatypeConverter.printBase64Binary(pwd.toString().getBytes()));
        urlConnection.setRequestProperty(PROPERTY_AUTHORIZATION, authentication.toString());
    }

    
    private String getResponseXML( HttpURLConnection urlConnection ) 
            throws IOException 
    {
        InputStream urlInputStream = null;
        try
        {
            //Get error stream or input stream depending on the response code
            urlInputStream = ( urlConnection.getResponseCode() >= HttpURLConnection.HTTP_BAD_REQUEST ) ? urlConnection.getErrorStream() : urlConnection.getInputStream();
        } 
        catch ( IOException e1 )
        {
            e1.printStackTrace();
        }
        BufferedReader bufferedReader = null;
        String responseData = null;
        StringBuilder responseDataBuilder = new StringBuilder();
        try
        {
            if (urlInputStream != null)
            {
                bufferedReader  = new BufferedReader( new InputStreamReader( urlInputStream ) );
                while( (responseData = bufferedReader.readLine()) != null ) 
                {
                    responseDataBuilder.append( responseData );
                }                
            }
        }         
        catch ( IOException e )
        {
            e.printStackTrace();
        }
        finally
        {
            if ( bufferedReader != null )
            {
                bufferedReader.close();
                bufferedReader = null;
            }
        }
        return ( responseDataBuilder.toString() );
    }
    
    public void clearCache()
    {
        model.clearCache();
    }
    
    public CallCenter findCallCenterBySubscriptionId(String subscriptionId)
    {
        return model.findCallCenterBySubscriptionId(subscriptionId);
    }
    
    
    public Agent findAgentBySubscriptionId(String subscriptionId)
    {
        return model.findAgentBySubscriptionId(subscriptionId);
    }
    
    public List<Agent> findAgentsByAgentId(String agentId)
    {
        return model.findAgentsByAgentId(agentId);
    }
}
