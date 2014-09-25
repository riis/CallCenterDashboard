package com.riis.model;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

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

}
