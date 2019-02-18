package com.mriganka.microservices.gateway_engine.web.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.mriganka.microservices.gateway_engine.dao.RouteMongoRepository;
import com.mriganka.microservices.gateway_engine.model.RouteEntity;
import com.mriganka.microservices.gateway_engine.model.view.View;
import com.mriganka.microservices.gateway_engine.service.ZuulRoutingService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by Mriganka Shekhar Roy on 2/18/2019.
 */
@RestController
@RequestMapping(path = "/api/v1/gateway-engine")
public class GatewayEngineController {

    private final static Logger LOGGER = LoggerFactory.getLogger(GatewayEngineController.class);

    @Autowired
    private ZuulRoutingService zuulRoutingService;

    @Autowired
    private RouteMongoRepository routeMongoRepository;

    @JsonView(View.v1.class)
    @ApiOperation(value = "Create route entity", produces = APPLICATION_JSON_UTF8_VALUE)
    @RequestMapping(method = POST, value = "/create", produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<RouteEntity> createRoute(
            @ApiParam(value = "RouteEntity", required = true)
            @RequestBody RouteEntity routeEntity) {
        return getResponse(zuulRoutingService.addRoute(routeEntity), OK);
    }

    @JsonView(View.v1.class)
    @ApiOperation(value = "Get all route entity from db", produces = APPLICATION_JSON_UTF8_VALUE)
    @RequestMapping(method = GET, produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Iterable<RouteEntity>> getAllRoute() {
        return getResponse(routeMongoRepository.findAll(), OK);
    }

    private <T> ResponseEntity<T> getResponse(T entity, HttpStatus status) {
        LOGGER.debug("response entity '{}' and status '{}'", entity, status);
        return new ResponseEntity<>(entity, status);
    }
}
