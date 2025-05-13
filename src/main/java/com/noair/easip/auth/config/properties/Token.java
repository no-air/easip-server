package com.noair.easip.auth.config.properties;

public record Token(
        String userId,
        TokenType tokenType,
        String provider
) {
}
