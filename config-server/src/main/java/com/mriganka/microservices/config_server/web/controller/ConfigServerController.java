package com.mriganka.microservices.config_server.web.controller;

import com.mriganka.microservices.config_server.dao.ConfigPropertiesRepository;
import com.mriganka.microservices.config_server.entity.ConfigProfile;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Mriganka Shekhar Roy on 12/18/2018.
 */
@RestController
@RequestMapping(path = "/")
@Api(value = "Config Server", tags = "Config Server",
        description = "Change, Create , Update and Delete properties from Config Server")
public class ConfigServerController {

    @Autowired
    private ConfigPropertiesRepository configPropertiesRepository;

    @ApiOperation(value = "Get applications list",
            notes = "Returns lists all applications configuration",
            responseContainer = "List",
            response = ConfigProfile.class)
    @RequestMapping(path = "/applications", method = {RequestMethod.GET}, produces = "application/json")
    public ResponseEntity<?> getApplicationsAndProfiles() {
        List<ConfigProfile> data = configPropertiesRepository.findAll();
        return ResponseEntity.ok(data);
    }

}
