import static junit.framework.Assert.assertEquals;

import org.junit.Test;

import com.riis.model.CallCenter;
import static junit.framework.Assert.assertNull;


public class CallCenterTest
{
    @Test
    public void testCallCenterConstruction()
    {
        CallCenter callCenter = new CallCenter();
        // constructor should set event Id to -1
        assertNull(callCenter.getCallCenterId());
    }

}
