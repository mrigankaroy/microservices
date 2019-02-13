package com.mriganka.microservices.gateway_engine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by Mriganka Shekhar Roy on 12/12/2018.
 */
@SpringBootApplication
@EnableZuulProxy
@ComponentScan("com.mriganka.microservices.gateway_engine")
public class GatewayEngineApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayEngineApplication.class, args);
    }
}
