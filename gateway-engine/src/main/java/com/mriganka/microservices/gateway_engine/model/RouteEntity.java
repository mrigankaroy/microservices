package com.mriganka.microservices.gateway_engine.model;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

/**
 * Created by Mriganka Shekhar Roy on 2/13/2019.
 */
@Slf4j
@Data
public class RouteEntity implements Serializable {

    private static final long serialVersionUID = 1;

    @Id
    public String routeKey;
    private String requestURI;
    private String targetURLHost;
    private int targetURLPort;
    private String targetURIPath;


}
