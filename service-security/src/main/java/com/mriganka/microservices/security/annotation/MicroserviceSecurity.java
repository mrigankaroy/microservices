package com.mriganka.microservices.security.annotation;

import com.mriganka.microservices.security.SecurityConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Mriganka Shekhar Roy on 2/12/2019.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({
        SecurityConfiguration.class
}
)
public @interface MicroserviceSecurity {
}
