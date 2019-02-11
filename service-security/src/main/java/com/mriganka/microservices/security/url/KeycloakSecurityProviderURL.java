package com.mriganka.microservices.security.url;

import org.springframework.beans.factory.annotation.Value;

/**
 * Created by mrro1015 on 12/29/2018.
 */
public class KeycloakSecurityProviderURL implements SecurityProviderURL {

    @Value("${security.provider:http://localhost:8080/auth}")
    private String securityProviderUrl;

    @Override
    public String getSecurityProviderUrl() {
        return securityProviderUrl;
    }
}
