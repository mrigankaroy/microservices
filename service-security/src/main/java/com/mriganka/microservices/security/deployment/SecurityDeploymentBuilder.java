package com.mriganka.microservices.security.deployment;

import org.keycloak.adapters.HttpClientBuilder;
import org.keycloak.adapters.KeycloakDeployment;
import org.keycloak.adapters.authentication.ClientCredentialsProvider;
import org.keycloak.adapters.rotation.HardcodedPublicKeyLocator;
import org.keycloak.adapters.rotation.PublicKeyLocator;
import org.keycloak.common.enums.SslRequired;
import org.keycloak.common.util.PemUtils;
import org.keycloak.enums.TokenStore;
import org.keycloak.representations.adapters.config.AdapterConfig;

import javax.validation.constraints.NotNull;
import java.security.PublicKey;

/**
 * Created by Mriganka Shekhar Roy on 8/24/2018.
 */
public class SecurityDeploymentBuilder {

    private final static String RESOURCE = "backend";
    private final static SslRequired SSL_REQUIRED = SslRequired.NONE;
    @NotNull
    private final String idpUrl;
    private String realm;
    private String realmKey;
    private KeycloakDeployment keycloakDeployment = new KeycloakDeployment();

    public SecurityDeploymentBuilder(@NotNull String idpUrl) {
        this.idpUrl = idpUrl;
    }

    public SecurityDeploymentBuilder withRealm(String realm) {
        this.realm = realm;
        return this;
    }

    public SecurityDeploymentBuilder withRealmKey(String realmKey) {
        this.realmKey = realmKey;
        return this;
    }

    public SecurityDeploymentBuilder forMitreId() {
        keycloakDeployment = new SecurityDeployment();
        return this;
    }

    public KeycloakDeployment build() {
        if (realm == null) {
            throw new RuntimeException("Configuration is wrong");
        }
        final AdapterConfig keycloakAdapterConfig = new AdapterConfig();
        keycloakAdapterConfig.setSslRequired(SSL_REQUIRED.name());
        keycloakAdapterConfig.setResource(RESOURCE);
        keycloakAdapterConfig.setRealm(realm);
        keycloakAdapterConfig.setRealmKey(realmKey);
        keycloakAdapterConfig.setAuthServerUrl(idpUrl);

        keycloakDeployment.setRealm(realm);
        keycloakDeployment.setResourceName(RESOURCE);
        if (realmKey != null) {
            try {
                PublicKey publicKey = PemUtils.decodePublicKey(realmKey);
                PublicKeyLocator locator = new HardcodedPublicKeyLocator(publicKey);
                keycloakDeployment.setPublicKeyLocator(locator);
            } catch (Exception e) {
                throw new RuntimeException("Could no decode public key", e);
            }
        }
        keycloakDeployment.setSslRequired(SSL_REQUIRED);

        keycloakDeployment.setTokenStore(TokenStore.SESSION);
        ClientCredentialsProvider clientAuthenticator = ClientCredentialsFactory
                .getInstance()
                .make(keycloakDeployment);
        keycloakDeployment.setClientAuthenticator(clientAuthenticator);
        keycloakDeployment.setClient(new HttpClientBuilder().build(keycloakAdapterConfig));
        keycloakDeployment.setAuthServerBaseUrl(keycloakAdapterConfig);
        keycloakDeployment.setBearerOnly(true);
        return new SecurityDeploymentWrapper(keycloakDeployment);
    }
}
