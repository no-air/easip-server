package com.noair.easip.auth.config.properties;

import java.security.InvalidParameterException;

public enum SocialLoginProvider {
    APPLE,
    GOOGLE;

    public static SocialLoginProvider fromString(String providerString) {
        return switch (providerString.toUpperCase()) {
            case "APPLE" -> APPLE;
            case "GOOGLE" -> GOOGLE;
            default -> throw new InvalidParameterException("Invalid social login provider: " + providerString);
        };
    }
}
