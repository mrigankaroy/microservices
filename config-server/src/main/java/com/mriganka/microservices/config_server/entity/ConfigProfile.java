package com.mriganka.microservices.config_server.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Mriganka Shekhar Roy on 12/11/2018.
 */
@Slf4j
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConfigProfile implements Serializable{

    public static final int DEFAULT_VERSION = 1;

    @Id
    private String id;
    protected String application;
    protected String profile = "default";
    protected int version = DEFAULT_VERSION;
    protected List<ConfigProperty> properties = new ArrayList<>();

    public void setProperties(Map<String, ConfigProperty> source) {
        this.properties = new ArrayList<>(source.values());
    }

    public Map<String, String> getPropertiesAsMap() {
        Map<String, String> result = new HashMap<>(properties.size());
        for (ConfigProperty property : properties) {
            result.put(property.getKey(), property.getValue());
        }
        return result;
    }
}
