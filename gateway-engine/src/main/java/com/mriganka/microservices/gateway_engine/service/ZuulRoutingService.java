package com.mriganka.microservices.gateway_engine.service;

import com.mriganka.microservices.gateway_engine.dao.RouteMongoRepository;
import com.mriganka.microservices.gateway_engine.model.RouteEntity;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.cloud.netflix.zuul.web.ZuulHandlerMapping;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.HashSet;

/**
 * Created by Mriganka Shekhar Roy on 2/13/2019.
 */
@CommonsLog
@Service
public class ZuulRoutingService {

    @Value("${gateway.protocol}")
    private static final String HTTP_PROTOCOL = "http://";

    private final ZuulProperties zuulProperties;
    private final ZuulHandlerMapping zuulHandlerMapping;
    private final RouteMongoRepository routeMongoRepository;

    @Autowired
    public ZuulRoutingService(final ZuulProperties zuulProperties, final ZuulHandlerMapping zuulHandlerMapping,
                              final RouteMongoRepository routeMongoRepository) {
        this.zuulProperties = zuulProperties;
        this.zuulHandlerMapping = zuulHandlerMapping;
        this.routeMongoRepository = routeMongoRepository;
    }


    @PostConstruct
    public void initialize() {
        try {
            routeMongoRepository.findAll().forEach(dynamicRoute -> {
                addRouteInZuul(dynamicRoute);
            });
            zuulHandlerMapping.setDirty(true);
        } catch (Exception e) {
            log.error("Exception in loading any previous route while restarting zuul routes.", e);
        }
    }

    private void addRouteInZuul(RouteEntity routeEntity) {
        String url = createTargetURL(routeEntity);
        zuulProperties.getRoutes().put(routeEntity.getRouteKey(),
                new ZuulProperties.ZuulRoute(routeEntity.getRouteKey(), routeEntity.getRequestURI() + "/**",
                        null, url, true, false, new HashSet<>()));
    }

    private String createTargetURL(RouteEntity routeEntity) {
        StringBuilder sb = new StringBuilder(HTTP_PROTOCOL);
        sb.append(routeEntity.getTargetURLHost()).append(":").append(routeEntity.getTargetURLPort());
        if (StringUtils.isEmpty(routeEntity.getTargetURIPath())) {
            sb.append("");
        } else {
            sb.append(routeEntity.getTargetURIPath());
        }
        String url = sb.toString();
        return url;
    }

    private void addToDB(RouteEntity routeEntity) {
        routeEntity = routeMongoRepository.insert(routeEntity);
        log.debug("Added in cache " + routeEntity);
    }

    private void removeFromDB(String routeKey) {
        log.debug("removing the dynamic route " + routeKey);
        routeMongoRepository.deleteById(routeKey);
    }


    public RouteEntity addRoute(RouteEntity routeEntity) {
        log.debug("request received in service to add "+ routeEntity);
        addRouteInZuul(routeEntity);

        log.debug("going to add in cache "+ routeEntity);
        addToDB(routeEntity);
        zuulHandlerMapping.setDirty(true);
        return routeEntity;
    }

    public Boolean removeDynamicRoute(final String routeKey) {
        if (zuulProperties.getRoutes().containsKey(routeKey)) {
            ZuulProperties.ZuulRoute zuulRoute = zuulProperties.getRoutes().remove(routeKey);
            log.debug("removed the zuul route "+ zuulRoute);
            removeFromDB(routeKey);
            zuulHandlerMapping.setDirty(true);
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

}
