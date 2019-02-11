package com.mriganka.microservices.security.deployment;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Mriganka Shekhar Roy on 8/24/2018.
 */
public class KeycloakRealmInfo {
    private String realm;

    @JsonProperty("public_key")
    private String publicKey;

    public String getRealm() {
        return realm;
    }

    public void setRealm(String realm) {
        this.realm = realm;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }
}
