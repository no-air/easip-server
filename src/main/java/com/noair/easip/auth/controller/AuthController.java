package com.noair.easip.auth.controller;

import com.noair.easip.auth.config.TokenGenerator;
import com.noair.easip.auth.config.properties.SocialLoginProvider;
import com.noair.easip.auth.config.properties.Token;
import com.noair.easip.auth.config.properties.TokenPair;
import com.noair.easip.auth.config.properties.TokenType;
import com.noair.easip.auth.controller.dto.AuthResultResponse;
import com.noair.easip.auth.controller.dto.NativeSocialLoginRequest;
import com.noair.easip.auth.controller.dto.RefreshAccessTokenRequest;
import com.noair.easip.auth.service.AuthService;
import com.noair.easip.member.controller.dto.CreateUserDto;
import com.noair.easip.member.controller.dto.request.CreateMemberRequest;
import com.noair.easip.member.domain.Member;
import com.noair.easip.member.exception.MemberAlreadyExistsException;
import com.noair.easip.member.service.MemberService;
import com.noair.easip.web.config.ErrorCode;
import com.noair.easip.web.controller.dto.ErrorResponse;
import com.noair.easip.web.controller.dto.ErrorType;
import com.noair.easip.web.exception.DomainException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Tag(name = "JWT 인증 API")
@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/v1/auth")
@ApiResponse(responseCode = "200", description = "성공")
@ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(
        mediaType = "application/json",
        schema = @Schema(implementation = ErrorResponse.class)))
public class AuthController {
    private final AuthService authService;
    private final MemberService memberService;
    private final TokenGenerator tokenGenerator;

    @Operation(summary = "네이티브 소셜 로그인")
    @PostMapping("/social/{provider}")
    AuthResultResponse socialLogin(
            @PathVariable("provider") @Parameter(description = "oAuth 제공자 이름", example = "GOOGLE")
            SocialLoginProvider provider,

            @RequestBody
            NativeSocialLoginRequest request
    ) {
        // oAuth 로그인 검증 (Apple 등)
        String identifier = authService.getIdentifierFromSocialToken(provider, request.socialToken());
        Optional<Member> member = memberService.getMemberBySocialAuthKey(provider, identifier);
        if (member.isEmpty()) { // 회원가입이 안된 경우 임시 토큰 발행
            TokenPair temporaryTokenPair = tokenGenerator.generateTemporaryTokenPair(provider, identifier);
            return AuthResultResponse.of(temporaryTokenPair, true);
        }

        // 사용자로 토큰 생성
        TokenPair tokenPair = tokenGenerator.generateTokenPair(member.get().getId());
        return AuthResultResponse.of(tokenPair, false);
    }

    @Operation(summary = "토큰 재발행")
    @PostMapping("/refresh")
    AuthResultResponse refreshAccessToken(
        @RequestBody
        RefreshAccessTokenRequest request
    ) {
        // 기존 리프레시 토큰 유효성 검증
        Token token = tokenGenerator.extractTokenData(request.refreshToken());
        if (token.tokenType() != TokenType.REFRESH) {
            throw new DomainException(ErrorCode.REFRESH_TOKEN_INVALID, ErrorType.NONE);
        }

        // 새 토큰 생성
        TokenPair tokenPair = tokenGenerator.generateTokenPair(token.userId());
        return AuthResultResponse.of(tokenPair, false);
    }

    @Operation(summary = "회원가입")
    @PreAuthorize("hasRole('TEMPORARY_MEMBER')")
    @PostMapping("/register")
    AuthResultResponse register(
            @Parameter(hidden = true)
            Authentication authentication,

            @RequestBody
            CreateMemberRequest request
    ) {
        // 토큰이 정상적으로 파싱되었고, 임시토큰이면 회원가입 진행
        if (authentication.getCredentials() instanceof Token(
                String userId, TokenType tokenType, String provider
        ) && tokenType == TokenType.TEMPORARY) {
            // identifier로 이미 있는 사용자인지 확인
            SocialLoginProvider socialLoginProvider = SocialLoginProvider.fromString(provider);
            Optional<Member> preExistsMember = memberService.getMemberBySocialAuthKey(socialLoginProvider, userId);
            if (preExistsMember.isPresent()) {
                throw new MemberAlreadyExistsException();
            }

            // 사용자 회원가입
            CreateUserDto createUserDto = new CreateUserDto(
                    socialLoginProvider,
                    userId,
                    request.name()
            );
            Member member = memberService.createMember(createUserDto);

            //새 토큰 생성
            TokenPair tokenPair = tokenGenerator.generateTokenPair(member.getId());
            return AuthResultResponse.of(tokenPair, false);
        }

        throw new DomainException(ErrorCode.AUTHORIZATION_FAILED);
    }
}
