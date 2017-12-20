package com.uangteman.core.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uangteman.common.utils.rest.RestResponse;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.ZuulFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class PostFilter extends ZuulFilter {

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

        ObjectMapper mapper = new ObjectMapper();
        RestResponse value = null;
        try {
            value = mapper.readValue(jsonString, RestResponse.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Request Method : " + request.getMethod() + " Request URL : " + request.getRequestURL().toString());
        return null;
    }

}
