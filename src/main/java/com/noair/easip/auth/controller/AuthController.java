package com.noair.easip.auth.controller;

import com.noair.easip.auth.config.TokenGenerator;
import com.noair.easip.auth.config.properties.SocialLoginProvider;
import com.noair.easip.auth.config.properties.TokenPair;
import com.noair.easip.auth.controller.dto.AuthResultResponse;
import com.noair.easip.auth.controller.dto.NativeSocialLoginRequest;
import com.noair.easip.auth.controller.dto.RefreshAccessTokenRequest;
import com.noair.easip.auth.service.AuthService;
import com.noair.easip.member.controller.dto.request.CreateNewMemberRequest;
import com.noair.easip.web.controller.dto.ErrorResponse;
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
//    private final MemberService memberService;
    private final TokenGenerator tokenGenerator;

    @Operation(summary = "네이티브 소셜 로그인")
    @PostMapping("/social/{provider}")
    AuthResultResponse socialLogin(
            @PathVariable("provider") @Parameter(description = "oAuth 제공자 이름", example = "GOOGLE")
            SocialLoginProvider provider,

            @RequestBody
            NativeSocialLoginRequest request
    ) {
        TokenPair tokenPair = tokenGenerator.generateTokenPair("1");
        return AuthResultResponse.of(tokenPair, false);
    }

    @Operation(summary = "토큰 재발행")
    @PostMapping("/refresh")
    AuthResultResponse refreshAccessToken(
        @RequestBody
        RefreshAccessTokenRequest request
    ) {
        TokenPair tokenPair = tokenGenerator.generateTokenPair("1");
        return AuthResultResponse.of(tokenPair, false);
    }

    @Operation(summary = "회원가입")
    @PreAuthorize("hasRole('TEMPORARY_MEMBER')")
    @PostMapping("/register")
    AuthResultResponse register(
            @Parameter(hidden = true)
            Authentication authentication,

            @RequestBody
            CreateNewMemberRequest request
    ) {
        TokenPair tokenPair = tokenGenerator.generateTokenPair("1");
        return AuthResultResponse.of(tokenPair, false);
    }
}
