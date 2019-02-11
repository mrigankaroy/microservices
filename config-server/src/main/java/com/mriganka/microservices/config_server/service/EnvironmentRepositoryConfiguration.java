package com.mriganka.microservices.config_server.service;

import org.springframework.cloud.config.server.environment.EnvironmentRepository;
import org.springframework.cloud.config.server.environment.SearchPathLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by mrro1015 on 12/11/2018.
 */
@Configuration
public class EnvironmentRepositoryConfiguration {
    @Bean
    public EnvironmentRepository getMongoDBEnvironmentRepository() {
        return new MongoEnvironmentRepository();
    }

    @Bean
    public SearchPathLocator getSearchPathLocator() {
        return null;
    }
}
