package com.noair.easip.auth.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@ConfigurationProperties(prefix = "app.token")
@ConfigurationPropertiesBinding
public record TokenProperties(
        String secretKey,
        @NestedConfigurationProperty TokenExpirationProperties expiration
) {
}
