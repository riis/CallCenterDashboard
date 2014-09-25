package com.riis.broadsoft;

import java.io.BufferedReader;
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

public class BroadsoftGateway
{
    private static final String PROTOCOL_SEPARATOR = "://";
    private static final String PATH_SEPARATOR = "/";
    private static final String REQUEST_METHOD = "GET";
    private static final String AUTH_TOKEN_SEPARATOR = ":";
    private static final String AUTH_PREFIX_BASIC = "Basic ";
    private static final String PROPERTY_AUTHORIZATION = "Authorization";

    private String protocol;
    private String actionPath;
    private String hostName;
    private String authenticationUsername;
    private String password;
    private String sessionCookie;
    
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
                String sessionCookie = urlConnection.getHeaderField(i);
            }
        }
    }
    
    public String makeRequest(String action) throws IOException
    {
        String responseXML = null;
        if (checkConfiguration())
        {
            URL url = new URL(protocol + PROTOCOL_SEPARATOR 
                    + hostName + PATH_SEPARATOR 
                    + actionPath + PATH_SEPARATOR 
                    + action );
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod(REQUEST_METHOD);
            setAuthorization(urlConnection);
            if (getSessionCookie() != null)
            {
                urlConnection.setRequestProperty("Cookie", sessionCookie);
            }
            setSessionCookie(urlConnection);
            responseXML = getResponseXML(urlConnection);
        }
        return responseXML;
    }
    
    public List<CallCenter> getAllCallCenters() throws IOException
    {
        String CallCenterXML =  makeRequest("user/gnolanUser1@xdp.broadsoft.com/directories/CallCenters?user=Supervisor");
        List<CallCenter> allCallCenters = new CallCenter().createListFromXMLString(CallCenterXML);
        for(CallCenter callCenter : allCallCenters)
        {
            String callCenterProfile = makeRequest("callcenter/" + callCenter.getCallCenterId() + "/profile");            
            callCenter.readNameFromXMLString(callCenterProfile);
            String callCenterCalls = makeRequest("callcenter/" + callCenter.getCallCenterId() + "/calls"); 
            callCenter.readQueueLengthFromXMLString(callCenterCalls);
        }
        return allCallCenters;
    }
    
    public List<Agent> getAllAgents() throws IOException
    {
        String agentXML =  makeRequest("user/gnolanUser1@xdp.broadsoft.com/directories/Agents");
        System.out.println("AgentXML = " + agentXML);
        List<Agent> allAgents = new Agent().createListFromXMLString(agentXML);
        for(Agent agent : allAgents)
        {
//            String callCenterProfile = makeRequest("callcenter/" + callCenter.getCallCenterId() + "/profile");            
//            callCenter.readNameFromXMLString(callCenterProfile);
//            String callCenterCalls = makeRequest("callcenter/" + callCenter.getCallCenterId() + "/calls"); 
//            callCenter.readQueueLengthFromXMLString(callCenterCalls);
        }
        return allAgents;
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
