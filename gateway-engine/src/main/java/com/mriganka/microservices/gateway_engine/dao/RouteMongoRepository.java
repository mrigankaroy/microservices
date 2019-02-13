package com.mriganka.microservices.gateway_engine.dao;

import com.mriganka.microservices.gateway_engine.model.RouteEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Mriganka Shekhar Roy on 2/13/2019.
 */
@Repository
public interface RouteMongoRepository extends MongoRepository<RouteEntity, String> {
}
