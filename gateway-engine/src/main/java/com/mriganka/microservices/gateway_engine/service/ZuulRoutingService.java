package com.mriganka.microservices.gateway_engine.service;

import com.mriganka.microservices.gateway_engine.dao.RouteMongoRepository;
import com.mriganka.microservices.gateway_engine.model.RouteEntity;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.cloud.netflix.zuul.web.ZuulHandlerMapping;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashSet;

/**
 * Created by Mriganka Shekhar Roy on 2/13/2019.
 */
//@CommonsLog
@Service
public class ZuulRoutingService {

    private final static Logger log = LoggerFactory.getLogger(ZuulRoutingService.class);

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
        if (StringUtils.isEmpty(routeEntity.getTargetServiceId())) {
            String url = createTargetURL(routeEntity);
            zuulProperties.getRoutes().put(routeEntity.getRouteKey(),
                    new ZuulProperties.ZuulRoute(routeEntity.getRouteKey(), routeEntity.getRequestURI() + "/**",
                            null, url, true, false, new HashSet<>()));
        } else {
            String uri = createTargetServiceURI(routeEntity);
            zuulProperties.getRoutes().put(routeEntity.getRouteKey(),
                    new ZuulProperties.ZuulRoute(routeEntity.getRouteKey(), routeEntity.getRequestURI() + "/**",
                            uri, null, true, false, new HashSet<>()));
        }
    }

    private String createTargetURL(RouteEntity routeEntity) {
        StringBuilder sb = new StringBuilder();
        if (StringUtils.isEmpty(routeEntity.getProtocol())) {
            sb.append(HTTP_PROTOCOL);
        } else {
            sb.append(routeEntity.getProtocol());
        }
        sb.append(routeEntity.getTargetURLHost()).append(":").append(routeEntity.getTargetURLPort());
        if (StringUtils.isEmpty(routeEntity.getTargetURIPath())) {
            sb.append("");
        } else {
            sb.append(routeEntity.getTargetURIPath());
        }
        return sb.toString();
    }


    private String createTargetServiceURI(RouteEntity routeEntity) {
        StringBuilder sb = new StringBuilder();
        if (StringUtils.isEmpty(routeEntity.getProtocol())) {
            sb.append(HTTP_PROTOCOL);
        } else {
            sb.append(routeEntity.getProtocol());
        }
        sb.append(routeEntity.getTargetServiceId());
        if (StringUtils.isEmpty(routeEntity.getTargetURIPath())) {
            sb.append("");
        } else {
            sb.append(routeEntity.getTargetURIPath());
        }
        return sb.toString();
    }

    private RouteEntity addToDB(RouteEntity routeEntity) {
        routeEntity = routeMongoRepository.insert(routeEntity);
        log.debug("Added in cache " + routeEntity);
        return routeEntity;
    }

    private void removeFromDB(String routeKey) {
        log.debug("removing the dynamic route " + routeKey);
        routeMongoRepository.deleteById(routeKey);
    }


    public RouteEntity addRoute(RouteEntity routeEntity) {
        log.debug("going to add in cache " + routeEntity);
        routeEntity = addToDB(routeEntity);
        log.debug("request received in service to add " + routeEntity);
        addRouteInZuul(routeEntity);
        zuulHandlerMapping.setDirty(true);
        return routeEntity;
    }

    public Boolean removeRoute(final String routeKey) {
        if (zuulProperties.getRoutes().containsKey(routeKey)) {
            ZuulProperties.ZuulRoute zuulRoute = zuulProperties.getRoutes().remove(routeKey);
            log.debug("removed the zuul route " + zuulRoute);
            removeFromDB(routeKey);
            zuulHandlerMapping.setDirty(true);
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

}
