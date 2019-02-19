package com.mriganka.microservices.gateway_engine.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

/**
 * Created by Mriganka Shekhar Roy on 2/13/2019.
 */
@Slf4j
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RouteEntity implements Serializable {

    private static final long serialVersionUID = 1;

    @Id
    private String routeKey;
    private String protocol;
    private String requestURI;
    private String targetURLHost;
    private int targetURLPort;
    private String targetURIPath;


}
