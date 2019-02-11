package com.mriganka.microservices.security.deployment;

import lombok.extern.apachecommons.CommonsLog;
import org.keycloak.adapters.KeycloakConfigResolver;
import org.keycloak.adapters.KeycloakDeployment;
import org.keycloak.adapters.rotation.AdapterRSATokenVerifier;
import org.keycloak.adapters.rotation.PublicKeyLocator;
import org.keycloak.adapters.spi.HttpFacade;
import org.keycloak.common.VerificationException;
import org.keycloak.jose.jws.JWSInput;
import org.keycloak.jose.jws.JWSInputException;
import org.keycloak.representations.AccessToken;

import javax.validation.constraints.NotNull;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mriganka Shekhar Roy on 8/24/2018.
 */
@CommonsLog
public class KeycloakConfigResolverImpl implements KeycloakConfigResolver {
    private static final int BEARER_TYPE_LENGTH = 7;
    private static final KeycloakDeployment DEFAULT_KEYCLOAK_DEPLOYMENT;

    static {
        KeycloakDeployment keycloakDeployment = new KeycloakDeployment();
        keycloakDeployment.setBearerOnly(true);
        keycloakDeployment.setPublicKeyLocator(new PublicKeyLocator() {
            @Override
            public PublicKey getPublicKey(String kid, KeycloakDeployment deployment) {
                return null;
            }

            @Override
            public void reset(KeycloakDeployment deployment) {

            }
        });
        DEFAULT_KEYCLOAK_DEPLOYMENT = new SecurityDeploymentWrapper(keycloakDeployment);
    }

    private final RealmKeyProvider realmKeyProvider;

    private final Map<DeploymentKey, KeycloakDeployment> deployments;

    public KeycloakConfigResolverImpl(RealmKeyProvider realmKeyProvider) {
        this.realmKeyProvider = realmKeyProvider;
        this.deployments = new HashMap();
    }

    @Override
    public KeycloakDeployment resolve(HttpFacade.Request request) {
        final String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null) {
          //  LOGGER.trace("No Authorization header, skipping keycloak deployment generation");
            return DEFAULT_KEYCLOAK_DEPLOYMENT;
        }

        if (authorizationHeader.length() > BEARER_TYPE_LENGTH) {
            return generateKeycloakDeploymentFromAuthorizationHeader(authorizationHeader);
        } else {
         //   LOGGER.debug("Authorization header is too short");
            return DEFAULT_KEYCLOAK_DEPLOYMENT;
        }
    }

    private KeycloakDeployment generateKeycloakDeploymentFromAuthorizationHeader(String authorizationHeader) {
        final String tokenString = authorizationHeader.substring(BEARER_TYPE_LENGTH);
        try {
            DeploymentKey deploymentKey = getDeploymentKey(tokenString);
            KeycloakDeployment deployment = deployments.get(deploymentKey);
            if (deployment != null && checkPublicKey(tokenString, deployment)) {
                // use cached deployment since it is ok for specified token
                return deployment;
            } else {
                // deployment does not exist or incorrect. Try to get a new public key
                String realmKeyForToken = realmKeyProvider.getRealmKey(deploymentKey.getRealm());
                if (realmKeyForToken != null) {
                    // received a new public key. Create a deployment
                    KeycloakDeployment keycloakDeployment = constructIdpDeployment(deploymentKey,
                            realmKeyForToken).build();
                    deployments.put(deploymentKey, keycloakDeployment);
                    if (checkPublicKey(tokenString, keycloakDeployment)) {
                        return keycloakDeployment;
                    }
                } else if (deployment != null) {
                    return DEFAULT_KEYCLOAK_DEPLOYMENT;
                }
            }
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return DEFAULT_KEYCLOAK_DEPLOYMENT;
    }

    protected SecurityDeploymentBuilder constructIdpDeployment(@NotNull DeploymentKey deploymentKey,
                                                               @NotNull String realmKeyForToken) {
        /*log.debug("Create idp deployment for configuration:\n" +
                        "realm: {}\n" +
                        "identity-provider-url: {}\n" +
                        "realmKey: {}",
                deploymentKey.getRealm(),
                deploymentKey.getSecurityProviderUrl(),
                realmKeyForToken);*/
        return new SecurityDeploymentBuilder(deploymentKey.getSecurityProviderUrl())
                .withRealm(deploymentKey.getRealm())
                .withRealmKey(realmKeyForToken);
    }

    /**
     * Extracts deployment key from issuer of access token
     *
     * @param tokenString access token string
     * @return deployment key
     */
    @NotNull
    protected DeploymentKey getDeploymentKey(@NotNull String tokenString) throws Exception {
        try {
            final AccessToken accessToken = new JWSInput(tokenString).readJsonContent(AccessToken.class);
            String issuer = accessToken.getIssuer();

            final String[] split = issuer.split("/realms/");
            if (split.length != 2) {
               // log.warn("Failed to retrieve tenant and auth server from issuer '{}', split size - {}", issuer, split.length);
                throw new Exception("Tenant not found in authorization header with issuer=" + issuer);
            }
            return new DeploymentKey(split[0], split[1]);
        } catch (JWSInputException e) {
            log.error("Failed to parse JWT token", e);
            throw new RuntimeException(e);
        }
    }


    /**
     * This method checks realm key for validate JWT token
     *
     * @param tokenString JWT token
     * @param deployment  public key from server
     * @return true is key is valid
     */
    private boolean checkPublicKey(@NotNull String tokenString,
                                   @NotNull KeycloakDeployment deployment) {
        try {
            AdapterRSATokenVerifier.verifyToken(tokenString, deployment, true, true);
        } catch (VerificationException e) {
            log.error("Error to validate token with public key", e);
            return false;
        }
        return true;
    }
}
