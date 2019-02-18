package com.mriganka.microservices.gateway_engine.web.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.logging.Logger;

/**
 * Created by Mriganka Shekhar Roy on 12/18/2018.
 */
public class LogInterceptor implements HandlerInterceptor {

    private static final Logger LOGGER = Logger.getLogger(LogInterceptor.class.getName());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        LOGGER.info(request.getRequestURL().toString());
        return true;
    }
}
