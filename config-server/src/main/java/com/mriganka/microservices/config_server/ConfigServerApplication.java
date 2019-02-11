package com.mriganka.microservices.config_server;

import com.mriganka.microservices.config_server.service.EnvironmentRepositoryConfiguration;
import com.mriganka.microservices.config_server.service.MongoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


/**
 * Created by mrro1015 on 12/11/2018.
 */

@SpringBootApplication
@EnableConfigServer
//@EnableWebMvc
@Import({
        EnvironmentRepositoryConfiguration.class,
        MongoConfiguration.class})
public class ConfigServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConfigServerApplication.class, args);
    }
}
