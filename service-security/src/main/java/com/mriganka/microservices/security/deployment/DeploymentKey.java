package com.mriganka.microservices.security.deployment;

import javax.validation.constraints.NotNull;

/**
 * Created by Mriganka Shekhar Roy on 8/24/2018.
 */
public class DeploymentKey {

    @NotNull
    private final String securityProviderUrl;
    @NotNull
    private final String realm;

    public DeploymentKey(@NotNull String securityProviderUrl,
                         @NotNull String realm) {
        this.securityProviderUrl = securityProviderUrl;
        this.realm = realm;
    }

    public @NotNull String getSecurityProviderUrl() {
        return securityProviderUrl;
    }

    public @NotNull String getRealm() {
        return realm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DeploymentKey)) return false;

        DeploymentKey that = (DeploymentKey) o;

        if (!securityProviderUrl.equals(that.securityProviderUrl)) return false;
        return realm.equals(that.realm);
    }

    @Override
    public int hashCode() {
        int result = securityProviderUrl.hashCode();
        result = 31 * result + realm.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "DeploymentKey{" +
                "securityProviderUrl='" + securityProviderUrl + '\'' +
                ", realm='" + realm + '\'' +
                '}';
    }
}
