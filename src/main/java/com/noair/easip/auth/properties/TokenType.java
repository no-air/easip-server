package com.noair.easip.auth.properties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum TokenType {
    ACCESS, REFRESH, TEMPORARY;

    public static TokenType fromString(String typeString) {
        return switch (typeString.toUpperCase()) {
            case "ACCESS" -> ACCESS;
            case "REFRESH" -> REFRESH;
            case "TEMPORARY" -> TEMPORARY;
            default -> throw new IllegalArgumentException("Invalid token type: " + typeString);
        };
    }
}
