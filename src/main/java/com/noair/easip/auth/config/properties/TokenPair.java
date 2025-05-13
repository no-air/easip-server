package com.noair.easip.auth.config.properties;

public record TokenPair(
        String accessToken,
        String refreshToken
) {
}
