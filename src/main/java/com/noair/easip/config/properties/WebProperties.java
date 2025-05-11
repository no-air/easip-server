package com.noair.easip.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.regex.Pattern;

@ConfigurationProperties(prefix = "app.web")
@ConfigurationPropertiesBinding
public record WebProperties(
        boolean versionFilterEnabled,
        String[] urlWhitelists,
        String[] versionCheckWhitelists,
        String[] urlNoLogging,
        @NestedConfigurationProperty WebHeaderNameProperties headerNames
) {
    public boolean isWhitelisted(String path) {
        for (String pattern : urlWhitelists) {
            if (isMatchPattern(pattern, path)) {
                return true;
            }
        }
        return false;
    }

    public boolean isVersionCheckWhitelisted(String path) {
        for (String pattern : versionCheckWhitelists) {
            if (isMatchPattern(pattern, path)) {
                return true;
            }
        }
        return false;
    }

    public boolean isNoLoggable(String path) {
        for (String pattern : urlNoLogging) {
            if (isMatchPattern(pattern, path)) {
                return true;
            }
        }
        return false;
    }

    private boolean isMatchPattern(String pattern, String input) {
        String regex = pattern.replace("*", ".*");
        return Pattern.matches(regex, input);
    }
}
