package com.mriganka.microservices.security;

import com.mriganka.microservices.security.deployment.KeycloakConfigResolverImpl;
import com.mriganka.microservices.security.deployment.KeycloakRealmKeyProvider;
import com.mriganka.microservices.security.deployment.RealmKeyProvider;
import com.mriganka.microservices.security.url.KeycloakSecurityProviderURL;
import com.mriganka.microservices.security.url.SecurityProviderURL;
import org.keycloak.adapters.KeycloakConfigResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Mriganka Shekhar Roy on 2/12/2019.
 */
@Configuration
public class KeycloakConfiguration {

    @Autowired
    @Bean
    public KeycloakConfigResolver keycloakConfigResolver(RealmKeyProvider realmKeyProvider) {
        return new KeycloakConfigResolverImpl(realmKeyProvider);
    }

    @Bean
    public RealmKeyProvider getRealmKeyProvider(SecurityProviderURL securityProviderURL) {
        return new KeycloakRealmKeyProvider(securityProviderURL);
    }

    @Bean
    public SecurityProviderURL getIdentityProviderUrlProvider() {
        return new KeycloakSecurityProviderURL();
    }
}
