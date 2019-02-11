package com.mriganka.microservices.security.deployment;

import org.apache.http.client.HttpClient;
import org.keycloak.adapters.KeycloakDeployment;
import org.keycloak.adapters.authentication.ClientCredentialsProvider;
import org.keycloak.adapters.authorization.PolicyEnforcer;
import org.keycloak.adapters.rotation.PublicKeyLocator;
import org.keycloak.common.enums.RelativeUrlsUsed;
import org.keycloak.common.enums.SslRequired;
import org.keycloak.common.util.KeycloakUriBuilder;
import org.keycloak.enums.TokenStore;
import org.keycloak.representations.adapters.config.AdapterConfig;

import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * Created by Mriganka Shekhar Roy on 8/24/2018.
 */
public class SecurityDeploymentWrapper extends KeycloakDeployment {

    private final @NotNull KeycloakDeployment keycloakDeployment;

    SecurityDeploymentWrapper() {
        this.keycloakDeployment = new KeycloakDeployment();
    }

    public SecurityDeploymentWrapper(@NotNull KeycloakDeployment keycloakDeployment) {
        this.keycloakDeployment = keycloakDeployment;
    }

    @Override
    public boolean isConfigured() {
        return keycloakDeployment.isConfigured();
    }

    @Override
    public String getResourceName() {
        return keycloakDeployment.getResourceName();
    }

    @Override
    public void setResourceName(String resourceName) {
        throw new IllegalStateException("It is impossible to modify wrapper for keycloak deployment");
    }

    @Override
    public String getRealm() {
        return keycloakDeployment.getRealm();
    }

    @Override
    public void setRealm(String realm) {
        throw new IllegalStateException("It is impossible to modify wrapper for keycloak deployment");
    }

    @Override
    public PublicKeyLocator getPublicKeyLocator() {
        return keycloakDeployment.getPublicKeyLocator();
    }

    @Override
    public void setPublicKeyLocator(PublicKeyLocator publicKeyLocator) {
        throw new IllegalStateException("It is impossible to modify wrapper for keycloak deployment");
    }

    @Override
    public String getAuthServerBaseUrl() {
        return keycloakDeployment.getAuthServerBaseUrl();
    }

    @Override
    public void setAuthServerBaseUrl(AdapterConfig config) {
        throw new IllegalStateException("It is impossible to modify wrapper for keycloak deployment");
    }

    @Override
    public RelativeUrlsUsed getRelativeUrls() {
        return keycloakDeployment.getRelativeUrls();
    }

    @Override
    public String getRealmInfoUrl() {
        return keycloakDeployment.getRealmInfoUrl();
    }

    @Override
    public KeycloakUriBuilder getAuthUrl() {
        return keycloakDeployment.getAuthUrl();
    }

    @Override
    public String getTokenUrl() {
        return keycloakDeployment.getTokenUrl();
    }

    @Override
    public KeycloakUriBuilder getLogoutUrl() {
        return keycloakDeployment.getLogoutUrl();
    }

    @Override
    public String getAccountUrl() {
        return keycloakDeployment.getAccountUrl();
    }

    @Override
    public String getRegisterNodeUrl() {
        return keycloakDeployment.getRegisterNodeUrl();
    }

    @Override
    public String getUnregisterNodeUrl() {
        return keycloakDeployment.getUnregisterNodeUrl();
    }

    @Override
    public boolean isBearerOnly() {
        return keycloakDeployment.isBearerOnly();
    }

    @Override
    public void setBearerOnly(boolean bearerOnly) {
        throw new IllegalStateException("It is impossible to modify wrapper for keycloak deployment");
    }

    @Override
    public boolean isEnableBasicAuth() {
        return keycloakDeployment.isEnableBasicAuth();
    }

    @Override
    public void setEnableBasicAuth(boolean enableBasicAuth) {
        throw new IllegalStateException("It is impossible to modify wrapper for keycloak deployment");
    }

    @Override
    public boolean isPublicClient() {
        return keycloakDeployment.isPublicClient();
    }

    @Override
    public void setPublicClient(boolean publicClient) {
        throw new IllegalStateException("It is impossible to modify wrapper for keycloak deployment");
    }

    @Override
    public Map<String, Object> getResourceCredentials() {
        return keycloakDeployment.getResourceCredentials();
    }

    @Override
    public void setResourceCredentials(Map<String, Object> resourceCredentials) {
        throw new IllegalStateException("It is impossible to modify wrapper for keycloak deployment");
    }

    @Override
    public ClientCredentialsProvider getClientAuthenticator() {
        return keycloakDeployment.getClientAuthenticator();
    }

    @Override
    public void setClientAuthenticator(ClientCredentialsProvider clientAuthenticator) {
        throw new IllegalStateException("It is impossible to modify wrapper for keycloak deployment");
    }

    @Override
    public HttpClient getClient() {
        return keycloakDeployment.getClient();
    }

    @Override
    public void setClient(HttpClient client) {
        throw new IllegalStateException("It is impossible to modify wrapper for keycloak deployment");
    }

    @Override
    public String getScope() {
        return keycloakDeployment.getScope();
    }

    @Override
    public void setScope(String scope) {
        throw new IllegalStateException("It is impossible to modify wrapper for keycloak deployment");
    }

    @Override
    public SslRequired getSslRequired() {
        return keycloakDeployment.getSslRequired();
    }

    @Override
    public void setSslRequired(SslRequired sslRequired) {
        throw new IllegalStateException("It is impossible to modify wrapper for keycloak deployment");
    }

    @Override
    public TokenStore getTokenStore() {
        return keycloakDeployment.getTokenStore();
    }

    @Override
    public void setTokenStore(TokenStore tokenStore) {
        throw new IllegalStateException("It is impossible to modify wrapper for keycloak deployment");
    }

    @Override
    public String getStateCookieName() {
        return keycloakDeployment.getStateCookieName();
    }

    @Override
    public void setStateCookieName(String stateCookieName) {
        throw new IllegalStateException("It is impossible to modify wrapper for keycloak deployment");
    }

    @Override
    public boolean isUseResourceRoleMappings() {
        return keycloakDeployment.isUseResourceRoleMappings();
    }

    @Override
    public void setUseResourceRoleMappings(boolean useResourceRoleMappings) {
        throw new IllegalStateException("It is impossible to modify wrapper for keycloak deployment");
    }

    @Override
    public boolean isCors() {
        return keycloakDeployment.isCors();
    }

    @Override
    public void setCors(boolean cors) {
        throw new IllegalStateException("It is impossible to modify wrapper for keycloak deployment");
    }

    @Override
    public int getCorsMaxAge() {
        return keycloakDeployment.getCorsMaxAge();
    }

    @Override
    public void setCorsMaxAge(int corsMaxAge) {
        throw new IllegalStateException("It is impossible to modify wrapper for keycloak deployment");
    }

    @Override
    public String getCorsAllowedHeaders() {
        return keycloakDeployment.getCorsAllowedHeaders();
    }

    @Override
    public void setCorsAllowedHeaders(String corsAllowedHeaders) {
        throw new IllegalStateException("It is impossible to modify wrapper for keycloak deployment");
    }

    @Override
    public String getCorsAllowedMethods() {
        return keycloakDeployment.getCorsAllowedMethods();
    }

    @Override
    public void setCorsAllowedMethods(String corsAllowedMethods) {
        throw new IllegalStateException("It is impossible to modify wrapper for keycloak deployment");
    }

    @Override
    public boolean isExposeToken() {
        return keycloakDeployment.isExposeToken();
    }

    @Override
    public void setExposeToken(boolean exposeToken) {
        throw new IllegalStateException("It is impossible to modify wrapper for keycloak deployment");
    }

    @Override
    public int getNotBefore() {
        return keycloakDeployment.getNotBefore();
    }

    @Override
    public void setNotBefore(int notBefore) {
        throw new IllegalStateException("It is impossible to modify wrapper for keycloak deployment");
    }

    @Override
    public boolean isAlwaysRefreshToken() {
        return keycloakDeployment.isAlwaysRefreshToken();
    }

    @Override
    public void setAlwaysRefreshToken(boolean alwaysRefreshToken) {
        throw new IllegalStateException("It is impossible to modify wrapper for keycloak deployment");
    }

    @Override
    public boolean isRegisterNodeAtStartup() {
        return keycloakDeployment.isRegisterNodeAtStartup();
    }

    @Override
    public void setRegisterNodeAtStartup(boolean registerNodeAtStartup) {
        throw new IllegalStateException("It is impossible to modify wrapper for keycloak deployment");
    }

    @Override
    public int getRegisterNodePeriod() {
        return keycloakDeployment.getRegisterNodePeriod();
    }

    @Override
    public void setRegisterNodePeriod(int registerNodePeriod) {
        throw new IllegalStateException("It is impossible to modify wrapper for keycloak deployment");
    }

    @Override
    public String getPrincipalAttribute() {
        return keycloakDeployment.getPrincipalAttribute();
    }

    @Override
    public void setPrincipalAttribute(String principalAttribute) {
        throw new IllegalStateException("It is impossible to modify wrapper for keycloak deployment");
    }

    @Override
    public boolean isTurnOffChangeSessionIdOnLogin() {
        return keycloakDeployment.isTurnOffChangeSessionIdOnLogin();
    }

    @Override
    public void setTurnOffChangeSessionIdOnLogin(boolean turnOffChangeSessionIdOnLogin) {
        throw new IllegalStateException("It is impossible to modify wrapper for keycloak deployment");
    }

    @Override
    public int getTokenMinimumTimeToLive() {
        return keycloakDeployment.getTokenMinimumTimeToLive();
    }

    @Override
    public void setTokenMinimumTimeToLive(int tokenMinimumTimeToLive) {
        throw new IllegalStateException("It is impossible to modify wrapper for keycloak deployment");
    }

    @Override
    public PolicyEnforcer getPolicyEnforcer() {
        return keycloakDeployment.getPolicyEnforcer();
    }

    @Override
    public void setPolicyEnforcer(PolicyEnforcer policyEnforcer) {
        throw new IllegalStateException("It is impossible to modify wrapper for keycloak deployment");
    }
}
