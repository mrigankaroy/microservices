package com.mriganka.microservices.security.deployment;

import lombok.extern.apachecommons.CommonsLog;
import org.keycloak.adapters.KeycloakDeployment;
import org.keycloak.adapters.authentication.ClientCredentialsProvider;
import org.keycloak.adapters.authentication.ClientIdAndSecretCredentialsProvider;

import java.util.*;

/**
 * Created by Mriganka Shekhar Roy on 12/29/2018.
 */
@CommonsLog
public class ClientCredentialsFactory {

    private final Map<String, Class<? extends ClientCredentialsProvider>> authenticators = new HashMap<>();

    private ClientCredentialsFactory() {
        initAuthenticators(Thread.currentThread().getContextClassLoader(),
                org.keycloak.adapters.authentication.ClientCredentialsProviderUtils.class.getClassLoader());
    }

    static ClientCredentialsFactory getInstance() {
        return Holder.INSTANCE;
    }

    private void initAuthenticators(ClassLoader... classLoaders) {
        Arrays.stream(classLoaders)
                .map(classLoader -> ServiceLoader.load(ClientCredentialsProvider.class, classLoader))
                .forEach(providers -> {
                    for (ClientCredentialsProvider clientCredentialsProvider : providers) {
                        try {
                            log.debug("Found ClientCredentialsProvider=" + clientCredentialsProvider.getId());
                            authenticators.put(clientCredentialsProvider.getId(), clientCredentialsProvider.getClass());
                        } catch (ServiceConfigurationError e) {
                            log.debug("Could not load ClientCredentialsProvider", e);
                        }
                    }
                });
    }

    ClientCredentialsProvider make(KeycloakDeployment deployment) {
        String clientId = deployment.getResourceName();
        Map<String, Object> clientCredentials = deployment.getResourceCredentials();
        String authenticatorId = getAuthenticatorId(clientId, clientCredentials);
        log.debug("Use provider=" + authenticatorId + " for authentication of client=" + clientId);
        Class<? extends ClientCredentialsProvider> clazz = authenticators.get(authenticatorId);
        if (clazz == null) {
            throw new RuntimeException("Couldn't find ClientCredentialsProvider implementation class with id: " + authenticatorId + ". " +
                    "Loaded authentication providers: " + authenticators.keySet());
        }
        try {
            ClientCredentialsProvider authenticator = clazz.newInstance();
            Object config = (clientCredentials == null) ? null : clientCredentials.get(authenticatorId);
            authenticator.init(deployment, config);
            return authenticator;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("Couldn't make authenticator instance of " + clazz);
        }
    }

    private String getAuthenticatorId(String clientId,
                                      Map<String, Object> clientCredentials) {
        if (clientCredentials == null || clientCredentials.isEmpty()) {
            return ClientIdAndSecretCredentialsProvider.PROVIDER_ID;
        } else {
            String authenticatorId = (String) clientCredentials.get("provider");
            if (authenticatorId == null) {
                if (clientCredentials.size() == 1) {
                    return clientCredentials.keySet().iterator().next();
                } else {
                    throw new RuntimeException("Could not identify clientAuthenticator from the configuration of client=" + clientId);
                }
            } else {
                return authenticatorId;
            }
        }
    }

    private static class Holder {
        static final ClientCredentialsFactory INSTANCE = new ClientCredentialsFactory();
    }
}
