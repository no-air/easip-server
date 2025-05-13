package com.noair.easip.auth.controller.dto;

public record AppleKeyResponse(
        String kty,
        String kid,
        String use,
        String alg,
        String n,
        String e
) {
}
