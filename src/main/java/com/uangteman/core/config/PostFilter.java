package com.uangteman.core.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.ZuulFilter;
import com.uangteman.core.controller.HelperController;
import org.hibernate.id.uuid.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class PostFilter extends ZuulFilter {

    @Autowired
    private HelperController helper;

    @Value( "${zuul.routes.master-service.url}" )
    private String masterServiceUrl;

    @Override
    public String filterType() {
        return "post";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        InputStream is = ctx.getResponseDataStream();
        String json = helper.getStringFromInputStream(is);

        // Get and Extract Path
        URL aURL = null;
        try {
            aURL = new URL(request.getRequestURL().toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        String jsonInString = "";
        if(aURL.getPath().equals("/core-service/master-service/age-range")){
            jsonInString = ts.getAgeRange(json, masterServiceUrl);
            ctx.setResponseBody(jsonInString);
        }
        else {
            ctx.setResponseBody(json);
        }

        System.out.println("Request Method : " + request.getMethod() + " Request URL : " + request.getRequestURL().toString());
        return null;
    }

}
