package com.mriganka.microservices.security.deployment;

import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;

/**
 * Created by mrro1015 on 8/24/2018.
 */
public interface RealmKeyProvider {
    @Nullable
    String getRealmKey(@NotNull String realm) throws Exception;
}
