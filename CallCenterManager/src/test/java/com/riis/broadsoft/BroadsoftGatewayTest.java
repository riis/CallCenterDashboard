import static junit.framework.Assert.assertEquals;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

import com.riis.broadsoft.BroadsoftGateway;
import com.riis.model.CallCenter;

import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertNotNull;


public class BroadsoftGatewayTest
{
    @Test
    public void testCallWithNoConfiguration()
    {
        try
        {
            BroadsoftGateway gateway = new BroadsoftGateway();
            String response = gateway.makeRequest("profile");
            assertNull(response);           
        }
        catch (IOException e)
        {
            System.err.print("Exception Thrown in BroadsoftGateWayTest: " + e.getMessage());
        }
     }

    
    @Test
    public void testCallPartialConfiguration()
    {
        try
        {
            BroadsoftGateway gateway = new BroadsoftGateway();
            gateway.setHostName("xsp2.xdp.broadsoft.com");
            gateway.setAuthenticationUsername("gnolanAdmin1@xdp.broadsoft.com");
            gateway.setPassword("welcome1");            
            String response = gateway.makeRequest("directories/Agents");
            assertNull(response);           
        }
        catch (IOException e)
        {
            System.err.print("Exception Thrown in BroadsoftGateWayTest: " + e.getMessage());
        }
     }


    @Test
    public void testCallFullConfigurationGetAllCallCenters()
    {
        try
        {
            BroadsoftGateway gateway = new BroadsoftGateway();
            gateway.setProtocol("http");
            gateway.setHostName("xsp2.xdp.broadsoft.com");
            gateway.setActionPath("com.broadsoft.xsi-actions/v2.0");
            gateway.setAuthenticationUsername("gnolanAdmin1@xdp.broadsoft.com");
            gateway.setPassword("welcome1"); 
            String response = gateway.makeRequest("user/gnolanUser1@xdp.broadsoft.com/directories/CallCenters?user=Supervisor");
            assertNotNull(response);
        }
        catch (IOException e)
        {
            System.err.print("Exception Thrown in BroadsoftGateWayTest: " + e.getMessage());
        }
     }

    @Test
    public void testCallFullConfigurationCallCenterProfile()
    {
        try
        {
            BroadsoftGateway gateway = new BroadsoftGateway();
            gateway.setProtocol("http");
            gateway.setHostName("xsp2.xdp.broadsoft.com");
            gateway.setActionPath("com.broadsoft.xsi-actions/v2.0");
            gateway.setAuthenticationUsername("gnolanAdmin1@xdp.broadsoft.com");
            gateway.setPassword("welcome1"); 
            String callCenterProfile = gateway.makeRequest("callcenter/CallCenterPrem@xdp.broadsoft.com/profile");            
            assertNotNull(callCenterProfile);
        }
        catch (IOException e)
        {
            System.err.print("Exception Thrown in BroadsoftGateWayTest: " + e.getMessage());
        }
     }
    
    @Test
    public void testCallFullConfigurationCallCenterQueues()
    {
        try
        {
            BroadsoftGateway gateway = new BroadsoftGateway();
            gateway.setProtocol("http");
            gateway.setHostName("xsp2.xdp.broadsoft.com");
            gateway.setActionPath("com.broadsoft.xsi-actions/v2.0");
            gateway.setAuthenticationUsername("gnolanAdmin1@xdp.broadsoft.com");
            gateway.setPassword("welcome1"); 
            String callCenterCalls = gateway.makeRequest("callcenter/CallCenterPrem@xdp.broadsoft.com/calls"); 
            assertNotNull(callCenterCalls);
        }
        catch (IOException e)
        {
            System.err.print("Exception Thrown in BroadsoftGateWayTest: " + e.getMessage());
        }
     }

    @Test
    public void testGetAllCallCenters()
    {
        try
        {
            // Need to eventually inject this test with Mock Objects
            BroadsoftGateway gateway = new BroadsoftGateway();
            gateway.setProtocol("http");
            gateway.setHostName("xsp2.xdp.broadsoft.com");
            gateway.setActionPath("com.broadsoft.xsi-actions/v2.0");
            gateway.setAuthenticationUsername("gnolanAdmin1@xdp.broadsoft.com");
            gateway.setPassword("welcome1"); 
            List<CallCenter> allCallCenters = gateway.getAllCallCenters();
            assertEquals(3, allCallCenters.size());
            CallCenter premCallCenter = allCallCenters.get(0);
            assertEquals("CallCenterPrem@xdp.broadsoft.com", premCallCenter.getCallCenterId());
            assertEquals("CallCenterPrem", premCallCenter.getCallCenterName());
            assertEquals(0, premCallCenter.getQueueLength());       
        }
        catch (IOException e)
        {
            System.err.print("Exception Thrown in BroadsoftGateWayTest: " + e.getMessage());
        }
    }
}
