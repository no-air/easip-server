package com.noair.easip.config.properties;

import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;

@ConfigurationPropertiesBinding
public record WebHeaderNameProperties(
        String accessToken,
        String proxyForwardHeader,
        String appVersionHeader,
        String platformHeader,
        String userIdHeader,
        String appKeyHeader
) {
    public WebHeaderNameProperties {
        if (accessToken == null) throw new IllegalArgumentException("accessToken cannot be null");
        if (proxyForwardHeader == null) throw new IllegalArgumentException("proxyForwardHeader cannot be null");
    }
}
