package com.mriganka.microservices.config_server.service;

import com.mriganka.microservices.config_server.dao.ConfigPropertiesRepository;
import com.mriganka.microservices.config_server.entity.ConfigProfile;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.config.environment.Environment;
import org.springframework.cloud.config.environment.PropertySource;
import org.springframework.cloud.config.server.environment.EnvironmentRepository;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Mriganka Shekhar Roy on 12/11/2018.
 */
public class MongoEnvironmentRepository implements EnvironmentRepository {

    public static final String CONFIG_PROPERTIES_GLOBAL_APPLICATION_NAME = "global";
    public static final String CONFIG_PROPERTIES_DEFAULT_PROFILE_NAME = "default";
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(MongoEnvironmentRepository.class);
    private static final String PROPERTY_SOURCE_MONGODB = "mongodb";

    @Autowired
    private ConfigPropertiesRepository configPropertiesRepository;


    public Environment findOne(String application, String activeProfile, String label) {

        log.debug("Receive config request for: {}, {}, {}", application, activeProfile, label);

        String[] activeProfiles = StringUtils.commaDelimitedListToStringArray(activeProfile);

        int environmentVersion = 0;
        List<ConfigProfile> globalProfiles = configPropertiesRepository.findByApplicationAndProfile(
                CONFIG_PROPERTIES_GLOBAL_APPLICATION_NAME, CONFIG_PROPERTIES_DEFAULT_PROFILE_NAME);

        Map<String, String> mergedProperties = new HashMap();

        for (ConfigProfile globalConfigProfile : globalProfiles) {
            mergedProperties.putAll(globalConfigProfile.getPropertiesAsMap());
            environmentVersion = Math.max(environmentVersion, globalConfigProfile.getVersion());
        }

        for (String profile : activeProfiles) {
            for (ConfigProfile configProfile : configPropertiesRepository.findByApplicationAndProfile(application, profile)) {
                mergedProperties.putAll(configProfile.getPropertiesAsMap());
                environmentVersion = Math.max(environmentVersion, configProfile.getVersion());
            }
        }

        Environment environment = new Environment(application, activeProfiles, label, String.valueOf(environmentVersion), null);
        environment.add(new PropertySource(PROPERTY_SOURCE_MONGODB, mergedProperties));
        return environment;
    }
}
