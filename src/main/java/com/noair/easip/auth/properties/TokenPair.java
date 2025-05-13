package com.noair.easip.auth.properties;

public record TokenPair(
        String accessToken,
        String refreshToken
) {
}
