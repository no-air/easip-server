package com.noair.easip.auth.config;

import com.noair.easip.auth.exception.TokenExpiredException;
import com.noair.easip.auth.exception.TokenNotValidException;
import com.noair.easip.auth.config.properties.*;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;
import java.util.Map;

@Component
public class JWTTokenGenerator implements TokenGenerator {
    private static final String USER_ID_KEY_NAME = "userId";
    private static final String PROVIDER_KEY_NAME = "provider";
    private static final String TOKEN_TYPE_KEY_NAME = "type";

    private final TokenProperties tokenProperties;
    private final Key signKey;

    public JWTTokenGenerator(TokenProperties tokenProperties) {
        this.tokenProperties = tokenProperties;

        byte[] secretKeyArray = tokenProperties.secretKey().getBytes();
        this.signKey = new SecretKeySpec(secretKeyArray, SignatureAlgorithm.HS256.getJcaName());
    }

    @Override
    public TokenPair generateTokenPair(String userId) {
        String accessToken = generateAccessToken(userId);
        String refreshToken = generateRefreshToken(userId);
        return new TokenPair(accessToken, refreshToken);
    }

    @Override
    public TokenPair generateTemporaryTokenPair(SocialLoginProvider provider, String identifier) {
        return new TokenPair(generateTemporaryToken(provider, identifier), null);
    }

    @Override
    public Token extractTokenData(String token) throws TokenNotValidException {
        try {
            Jws<Claims> tokenClaim = Jwts.parserBuilder().setSigningKey(signKey).build().parseClaimsJws(token);

            String tokenTypeStr = (String) tokenClaim.getHeader().get(TOKEN_TYPE_KEY_NAME);
            TokenType tokenType = TokenType.fromString(tokenTypeStr);
            String userId = tokenClaim.getBody().get(USER_ID_KEY_NAME, String.class);
            String provider = (String) tokenClaim.getHeader().get(PROVIDER_KEY_NAME);
            return new Token(userId, tokenType, provider);
        } catch (ExpiredJwtException exception) {
            throw new TokenExpiredException();
        } catch (Exception exception) {
            throw new TokenNotValidException();
        }
    }

    private String generateAccessToken(String userId) {
        return Jwts.builder()
                .setHeader(createTokenHeader(TokenType.ACCESS))
                .setClaims(Map.of(USER_ID_KEY_NAME, userId))
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(tokenProperties.expiration().accessToken())))
                .signWith(signKey, SignatureAlgorithm.HS256)
                .compact();
    }

    private String generateRefreshToken(String userId) {
        return Jwts.builder()
                .setHeader(createTokenHeader(TokenType.REFRESH))
                .setClaims(Map.of(USER_ID_KEY_NAME, userId))
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(tokenProperties.expiration().refreshToken())))
                .signWith(signKey, SignatureAlgorithm.HS256)
                .compact();
    }

    private String generateTemporaryToken(SocialLoginProvider provider, String identifier) {
        return Jwts.builder()
                .setHeader(createTokenHeader(TokenType.TEMPORARY))
                .setClaims(Map.of(PROVIDER_KEY_NAME, provider.name(), USER_ID_KEY_NAME, identifier))
                .setExpiration(new Date(Long.MAX_VALUE))
                .signWith(signKey, SignatureAlgorithm.HS256)
                .compact();
    }

    private Map<String, Object> createTokenHeader(TokenType tokenType) {
        return Map.of(
                "typ", "JWT",
                "alg", "HS256",
                "regDate", System.currentTimeMillis(),
                TOKEN_TYPE_KEY_NAME, tokenType.name()
        );
    }
}
