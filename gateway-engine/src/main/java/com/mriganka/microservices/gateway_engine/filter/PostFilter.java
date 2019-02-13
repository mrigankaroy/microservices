package com.mriganka.microservices.gateway_engine.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;

@CommonsLog
@Component
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
        RequestContext ctx = RequestContext.getCurrentContext();
        String requestURL = ctx.getRequest().getRequestURL().toString();
        return !(requestURL.contains("/gateway-proxy"));
    }

    @Override
    public Object run() {
        HttpServletResponse response = RequestContext.getCurrentContext().getResponse();
        log.info("PostFilter: " + String.format("response is %s", response));
        return null;
    }
}
