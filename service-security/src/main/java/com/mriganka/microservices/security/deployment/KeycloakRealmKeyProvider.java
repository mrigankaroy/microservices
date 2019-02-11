package com.mriganka.microservices.security.deployment;

import com.mriganka.microservices.security.url.KeycloakSecurityProviderURL;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.client.RestTemplate;

import javax.validation.constraints.NotNull;
import java.net.URI;

/**
 * Created by mrro1015 on 8/24/2018.
 */
public class KeycloakRealmKeyProvider implements RealmKeyProvider{

    private final @NotNull RestTemplate REST_TEMPLATE = new RestTemplate();
    private final @NotNull URI realmInfoUri;

    public KeycloakRealmKeyProvider(@NotNull KeycloakSecurityProviderURL identityProviderUrlProvider) {
        this.realmInfoUri = URI.create(identityProviderUrlProvider.getSecurityProviderUrl() + "/realms/");
    }

    @Nullable
    @Override
    public String getRealmKey(@NotNull String realm) throws Exception {
        final ResponseEntity<KeycloakRealmInfo> response;
        try {
            response = REST_TEMPLATE.exchange(realmInfoUri.resolve(realm),
                    HttpMethod.GET,
                    null,
                    KeycloakRealmInfo.class);
        } catch (Exception e) {
            throw e;
        }

        final String newKey = response.getBody().getPublicKey();
        return newKey;
    }
}
