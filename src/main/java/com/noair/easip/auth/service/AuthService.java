package com.noair.easip.auth.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.JWK;
import com.noair.easip.auth.config.properties.SocialLoginProvider;
import com.noair.easip.auth.controller.dto.AppleKeyListResponse;
import com.noair.easip.auth.controller.dto.AppleKeyResponse;
import com.noair.easip.web.config.ErrorCode;
import com.noair.easip.web.controller.dto.ErrorType;
import com.noair.easip.web.exception.DomainException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.security.InvalidParameterException;
import java.security.Key;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final GoogleIdTokenVerifier googleIdTokenVerifier;
    private final ObjectMapper objectMapper;

    public String getIdentifierFromSocialToken(SocialLoginProvider provider, String socialToken) {
        return switch (provider) {
            case GOOGLE -> getIdentifierFromGoogleToken(socialToken);
            case APPLE -> getIdentifierFromAppleToken(socialToken);
            default -> throw new IllegalArgumentException("Unsupported provider: " + provider);
        };
    }

    private String getIdentifierFromGoogleToken(String googleToken) {
        try {
            GoogleIdToken idToken = googleIdTokenVerifier.verify(googleToken);
            return idToken.getPayload().getSubject();

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new DomainException(ErrorCode.AUTHENTICATION_FAILED, ErrorType.ALERT);
        }
    }

    private String getIdentifierFromAppleToken(String appleToken) {
        AppleKeyResponse[] keys = retrieveAppleKeys();
        try {
            String[] tokenParts = appleToken.split("\\.");
            String headerPart = new String(Base64.getDecoder().decode(tokenParts[0]));
            JsonNode headerNode = objectMapper.readTree(headerPart);
            String kid = headerNode.get("kid").asText();

            AppleKeyResponse matchedKey = Arrays.stream(keys)
                    .filter(key -> key.kid().equals(kid))
                    .findFirst()
                    .orElseThrow(InvalidParameterException::new);

            return parseIdentifierFromAppleToken(matchedKey, appleToken);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new DomainException(ErrorCode.AUTHENTICATION_FAILED, ErrorType.ALERT);
        }
    }

    private AppleKeyResponse[] retrieveAppleKeys() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.put("Content-type", Collections.singletonList("application/x-www-form-urlencoded;charset=utf-8"));

        ResponseEntity<AppleKeyListResponse> keyListResponse = restTemplate.exchange(RequestEntity
                .get("https://appleid.apple.com/auth/keys")
                .headers(headers)
                .build(), AppleKeyListResponse.class);

        // 키 반환 실패시 throw
        if (!keyListResponse.getStatusCode().is2xxSuccessful())
            throw new DomainException(ErrorCode.AUTHENTICATION_FAILED, ErrorType.ALERT);

        return Objects.requireNonNull(keyListResponse.getBody()).keys();
    }

    private String parseIdentifierFromAppleToken(AppleKeyResponse matchedKey, String accessToken)
            throws JsonProcessingException, ParseException, JOSEException {
        Key keyData = JWK.parse(objectMapper.writeValueAsString(matchedKey)).toRSAKey().toRSAPublicKey();
        Jws<Claims> parsedClaims = Jwts.parserBuilder()
                .setSigningKey(keyData)
                .build()
                .parseClaimsJws(accessToken);

        return parsedClaims.getBody().get("sub", String.class);
    }
}
