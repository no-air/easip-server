package com.noair.easip.auth;

import com.noair.easip.auth.exception.TokenNotValidException;
import com.noair.easip.auth.properties.SocialLoginProvider;
import com.noair.easip.auth.properties.Token;
import com.noair.easip.auth.properties.TokenPair;

public interface TokenGenerator {
    /**
     * 토큰 페어 (엑세스토큰, 리프레시 토큰)을 생성합니다.
     *
     * @param userId 사용자 아이디
     * @return 토큰 페어 (엑세스 토큰, 리프레시 토큰)
     */
    TokenPair generateTokenPair(String userId);

    /**
     * 임시 토큰 페어 (엑세스토큰, 리프레시 토큰)을 생성합니다.
     *
     * @param provider   소셜 로그인 제공자
     * @param identifier 소셜 로그인 식별자
     * @return 임시 토큰 페어 (엑세스 토큰, 리프레시 토큰)
     */
    TokenPair generateTemporaryTokenPair(SocialLoginProvider provider, String identifier);

    /**
     * 토큰의 데이터를 추출합니다.
     * 유효하지 않은 경우, TokenNotValidException 이 발생합니다.
     *
     * @param token JWT 토큰
     * @return 토큰 데이터 DTO
     * @throws TokenNotValidException 토큰이 유효하지 않은 경우
     */
    Token extractTokenData(String token);
}
