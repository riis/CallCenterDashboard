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
//            gateway.setHostName("xsp2.xdp.broadsoft.com");
//            gateway.setUserName("gnolanUser1@xdp.broadsoft.com");
//            gateway.setPassword("welcome1");            
            gateway.setHostName("xsp.sip.voip.telnetww.com");
            gateway.setUserName("admin_017279@asmain.voip.telnetww.com");
            gateway.setPassword("xy7@g42!");            
            String response = gateway.makeRequest("profile");
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
            gateway.setHostName("xsp.sip.voip.telnetww.com");
            gateway.setActionPath("com.broadsoft.xsi-actions/v2.0/user");
            gateway.setUserName("admin_017279@asmain.voip.telnetww.com");
            gateway.setPassword("xy7@g42!"); 
            String response = gateway.makeRequest("profile");
            assertNotNull(response);
            System.out.println(response);
        }
        catch (IOException e)
        {
            System.err.print("Exception Thrown in BroadsoftGateWayTest: " + e.getMessage());
        }
     }

}
