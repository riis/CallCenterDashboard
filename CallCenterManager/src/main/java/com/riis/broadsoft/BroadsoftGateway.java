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
    private static final String PROTOCOL_SEPARATOR = "://";
    private static final String PATH_SEPARATOR = "/";
    private static final String AUTH_TOKEN_SEPARATOR = ":";
    private static final String AUTH_PREFIX_BASIC = "Basic ";
    private static final String PROPERTY_AUTHORIZATION = "Authorization";
    private static final String CALLBACK_ADDRESS = "ec2-54-205-41-129.compute-1.amazonaws.com:8080/" +
    		"CallCenterManager/webservices/callcentersubscriptioncallback";
    

    private String protocol;
    private String actionPath;
    private String hostName;
    private String authenticationUsername;
    private String password;
    private String sessionCookie;
    
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
            setSessionCookie(urlConnection);
            
            //FIXME: added code to pass XML body - need to test
            if (body != null)
            {
                urlConnection.setRequestProperty("Content-Length", "" + 
                        Integer.toString(body.getBytes().length));       
               //Send request
               DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream ());
               wr.writeBytes (body);
               wr.flush ();
               wr.close ();
            }
            
            responseXML = getResponseXML(urlConnection);
        }
        return responseXML;
    }
    
    public List<CallCenter> getAllCallCenters() throws IOException
    {
        if (model.getCallCenters() == null)
        {
            String CallCenterXML =  makeRequest("user/gnolanUser1@xdp.broadsoft.com/directories/CallCenters?user=Supervisor", 
                    REQUEST_METHOD_GET, null);
            List<CallCenter> allCallCenters = new CallCenter().createListFromXMLString(CallCenterXML);
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
            String agentXML =  makeRequest("user/gnolanUser1@xdp.broadsoft.com/directories/Agents",
                    REQUEST_METHOD_GET, null);
            List<Agent> allAgents = new Agent().createListFromXMLString(agentXML);
            for(Agent agent : allAgents)
            {
//                String callCenterProfile = makeRequest("callcenter/" + callCenter.getCallCenterId() + "/profile");            
//                callCenter.readNameFromXMLString(callCenterProfile);
//                String callCenterCalls = makeRequest("callcenter/" + callCenter.getCallCenterId() + "/calls"); 
//                callCenter.readQueueLengthFromXMLString(callCenterCalls);
                refreshAgentStatus(agent);
            }
            model.setAgents(allAgents);
        }
        return model.getAgents();
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
 //       System.out.println("AgentRefresh XML = " + refreshXML);
        agent.readStatusFromXMLString(refreshXML);
    }
    
    
    public void subscribeAllCallCenters() throws IOException
    {
        List<CallCenter> allCallCenters = getAllCallCenters();
        for (CallCenter callCenter : allCallCenters)
        {
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
        		        "<uri>" + CALLBACK_ADDRESS + "</uri>" +
        		    "</httpContact>" +
        		    "<applicationId>CallCenterDashboard</applicationId>" +
        		"</Subscription>";
        
        String requestString = "user/" + callCenterId;
        String CallCenterSubscriptionXML =  makeRequest(requestString, 
                REQUEST_METHOD_POST, null);
        System.out.println("CallCenterSubscription XML = " + CallCenterSubscriptionXML);
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
            bufferedReader  = new BufferedReader( new InputStreamReader( urlInputStream ) );
            while( (responseData = bufferedReader.readLine()) != null ) 
            {
                responseDataBuilder.append( responseData );
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
}
