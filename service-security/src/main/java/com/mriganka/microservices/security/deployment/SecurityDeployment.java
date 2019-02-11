package com.mriganka.microservices.security.deployment;

import org.keycloak.adapters.KeycloakDeployment;
import org.keycloak.common.util.KeycloakUriBuilder;

/**
 * Created by Mriganka Shekhar Roy on 8/24/2018.
 */
public class SecurityDeployment extends KeycloakDeployment {
    @Override
    protected void resolveUrls(KeycloakUriBuilder authUrlBuilder) {
        super.resolveUrls(authUrlBuilder);

        realmInfoUrl = authUrlBuilder.getScheme() + "://" + authUrlBuilder.getHost() + ":"
                + authUrlBuilder.getPort() + "/";
    }
}
