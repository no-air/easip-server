package com.noair.easip.auth.properties;

public record Token(
        String userId,
        TokenType tokenType,
        String provider
) {
}
