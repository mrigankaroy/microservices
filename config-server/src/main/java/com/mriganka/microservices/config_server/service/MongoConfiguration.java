package com.mriganka.microservices.config_server.service;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.Collections;

/**
 * Created by Mriganka Shekhar Roy on 12/11/2018.
 */
@Slf4j
@Configuration
public class MongoConfiguration {
    @Value("${mongo.host}")
    private String mongoHost;

    @Value("${mongo.port}")
    private Integer mongoPort;

    @Value("${mongo.user}")
    private String mongoUserName;

    @Value("${mongo.password}")
    private String mongoPassword;

    @Value("${mongo.dbName}")
    private String dbName;

    @Bean
    public MongoClient getMongo() throws Exception {
        ServerAddress serverAddress = new ServerAddress(mongoHost, mongoPort);
        MongoCredential credential = MongoCredential.createCredential(mongoUserName, dbName, mongoPassword.toCharArray());
        MongoClient client = new MongoClient(serverAddress, Collections.singletonList(credential));
        return client;
    }

    @Bean
    public MongoTemplate mongoTemplate(MongoClient mongoClient) throws Exception {
        return new MongoTemplate(mongoClient, dbName);
    }

}
