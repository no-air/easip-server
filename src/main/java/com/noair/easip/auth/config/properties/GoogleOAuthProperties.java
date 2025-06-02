package com.noair.easip.auth.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;

import java.util.List;

@ConfigurationProperties(prefix = "app.oauth")
@ConfigurationPropertiesBinding
public record GoogleOAuthProperties(
        List<String> googleClientIds
) {
}
