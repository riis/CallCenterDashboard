import static junit.framework.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

import com.riis.broadsoft.BroadsoftGateway;
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
    public void testCallFullConfiguration()
    {
        try
        {
            BroadsoftGateway gateway = new BroadsoftGateway();
            gateway.setProtocol("http");
            gateway.setHostName("xsp2.xdp.broadsoft.com");
            gateway.setActionPath("com.broadsoft.xsi-actions/v2.0/user");
            gateway.setUserName("gnolanUser1@xdp.broadsoft.com");
            gateway.setAuthenticationUsername("gnolanAdmin1@xdp.broadsoft.com");
            gateway.setPassword("welcome1"); 
            String response = gateway.makeRequest("directories/CallCenters?user=Supervisor");
            assertNotNull(response);
            System.out.println(response);
        }
        catch (IOException e)
        {
            System.err.print("Exception Thrown in BroadsoftGateWayTest: " + e.getMessage());
        }
     }

}
