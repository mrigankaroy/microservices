package com.mriganka.microservices.config_server.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by Mriganka Shekhar Roy on 12/11/2018.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConfigProperty implements Serializable{
    private String key;
    private String value;

}
