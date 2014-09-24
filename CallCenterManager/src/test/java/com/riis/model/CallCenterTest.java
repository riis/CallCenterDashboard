import static junit.framework.Assert.assertEquals;

import java.io.StringReader;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.riis.model.CallCenter;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.fail;


public class CallCenterTest
{
    DocumentBuilderFactory docBuilderFactory;
    DocumentBuilder docBuilder;
    Document doc;
    NodeList nodes;
    CallCenter callCenter;  
    static String XML_RESULT = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>" +
            "<CallCenters xmlns=\"http://schema.broadsoft.com/xsi\">" +
            "    <serviceUserID>Premium@172.16.25.102</serviceUserID>" +
            "    <serviceUserID>PremiumTwo@172.16.25.102</serviceUserID>" +
            "</CallCenters>";

    @Before
    public void setup()
    {
        try
        {
            docBuilderFactory = DocumentBuilderFactory.newInstance();
            docBuilder = docBuilderFactory.newDocumentBuilder();        
            callCenter = new CallCenter();
            StringReader stringReader = new StringReader(XML_RESULT);
            InputSource inputSource = new InputSource(stringReader);
            doc = docBuilder.parse(inputSource);
            doc.getDocumentElement().normalize();
            nodes = doc.getDocumentElement().getElementsByTagName("serviceUserID");              
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    
    @Test
    public void testReadFromXMLNode() throws Exception
    {      
        Node element = nodes.item(0);
        if (element != null)
        {
            callCenter.readFromXMLNode(element);            
        }
        else
        {
            fail("No Nodes found in list");
        }
        assertEquals("Premium@172.16.25.102",callCenter.getCallCenterId());
    }

    @Test
    public void testReadListFromXMLString() throws Exception
    {      
        List<CallCenter> result = callCenter.createListFromXMLString(XML_RESULT);   
        assertEquals(2, result.size());
        assertEquals("Premium@172.16.25.102",result.get(0).getCallCenterId());
        assertEquals("PremiumTwo@172.16.25.102",result.get(1).getCallCenterId());
    }
    
}
