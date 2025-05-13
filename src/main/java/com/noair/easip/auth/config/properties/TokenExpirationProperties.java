package com.noair.easip.auth.config.properties;

import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;

@ConfigurationPropertiesBinding
public record TokenExpirationProperties(
        String accessToken,
        String refreshToken
) {
    public TokenExpirationProperties {
        if (accessToken == null) throw new IllegalArgumentException("expiration period of accessToken cannot be null");
        if (refreshToken == null) throw new IllegalArgumentException("expiration period of refreshToken cannot be null");
    }
}
