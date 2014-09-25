package com.riis.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.riis.broadsoft.BroadsoftGateway;

@Controller
public class HomeController 
{
    private BroadsoftGateway gateway;
    
	@RequestMapping(value="/")
	public ModelAndView test(HttpServletResponse response) throws IOException
	{
//		return new ModelAndView("home");
	    Map<String, Object> myModel = new HashMap<String, Object>();
	    String now = (new Date()).toString();
	    myModel.put("now", now);
	    gateway = new BroadsoftGateway();
        gateway.setProtocol("http");
        gateway.setHostName("xsp2.xdp.broadsoft.com");
        gateway.setActionPath("com.broadsoft.xsi-actions/v2.0");
        gateway.setAuthenticationUsername("gnolanAdmin1@xdp.broadsoft.com");
        gateway.setPassword("welcome1"); 
	    myModel.put("callcenters", gateway.getAllCallCenters());

        return new ModelAndView("home", "model", myModel);
	}
}
