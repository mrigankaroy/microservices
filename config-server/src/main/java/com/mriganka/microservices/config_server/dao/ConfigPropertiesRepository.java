package com.mriganka.microservices.config_server.dao;

import com.mriganka.microservices.config_server.entity.ConfigProfile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

/**
 * Created by mrro1015 on 12/11/2018.
 */
public interface ConfigPropertiesRepository extends MongoRepository<ConfigProfile, String> {

    @Query("{ 'application': ?0, 'profile': ?1}")
    List<ConfigProfile> findByApplicationAndProfile(String application, String profile);
}
