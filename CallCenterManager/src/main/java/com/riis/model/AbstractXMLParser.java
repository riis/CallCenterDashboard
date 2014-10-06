package com.riis.model;

import java.util.Iterator;

import javax.xml.namespace.NamespaceContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.springframework.util.xml.SimpleNamespaceContext;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

public abstract class AbstractXMLParser implements XMLParserContract
{
    DocumentBuilderFactory docBuilderFactory;
    DocumentBuilder docBuilder;
    Document doc;
    XPath xPath;

    public AbstractXMLParser()
    {
        super();
        try
        {
            docBuilderFactory = DocumentBuilderFactory.newInstance();
            docBuilder = docBuilderFactory.newDocumentBuilder();        
            xPath = XPathFactory.newInstance().newXPath();            
        }
        catch (Exception e)
        {
            System.err.println("Could not initialize XML Document Builder");
            System.exit(-1);
        }
    }

    protected String readFromNodeWithPath(Node element, String path) throws Exception
    {
        XPathExpression xPathExpression = xPath.compile(path);
        String result = xPathExpression.evaluate(element);
        return result;
    }

    protected String getNodeValueWithPath(String path) throws Exception
    {
        XPathExpression xPathExpression = xPath.compile(path);
        String result = xPathExpression.evaluate(doc);
        return result;
    }

    protected String getNodeValueWithPathAndContext(String path) throws Exception
    {
//        SimpleNamespaceContext nsContext = new SimpleNamespaceContext();
//        nsContext.bindNamespaceUri("xsi", "http://schema.broadsoft.com/xsi");
//        xPath.setNamespaceContext(new NamespaceContext() 
//        {
//            @Override
//            public Iterator getPrefixes(String arg0) 
//            {
//                return null;
//            }
//
//            @Override
//            public String getPrefix(String arg0) 
//            {
//                return null;
//            }
//
//            @Override
//            public String getNamespaceURI(String arg0) 
//            {
//                if("xsi".equals(arg0)) 
//                {
//                    return "http://schema.broadsoft.com/xsi";
//                }
//                return null;
//            }
//        });
//        System.out.println("XPATH NAMESPACE CONTEXT = " + xPath.getNamespaceContext().getNamespaceURI("xsi"));
//        System.out.println("XPATH toString = :" + xPath.toString());
//        XPathExpression xPathExpression = xPath.compile(path);
//        String result = xPathExpression.evaluate(doc);
        XPath xpath = XPathFactory.newInstance().newXPath();        
        Node widgetNode = (Node)xpath.evaluate(path, doc, XPathConstants.NODE);
        return widgetNode.getNodeName();
        
    }
}
