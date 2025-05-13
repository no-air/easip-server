package com.noair.easip.web.controller;

import com.noair.easip.auth.config.TokenGenerator;
import com.noair.easip.auth.config.properties.TokenPair;
import com.noair.easip.auth.controller.dto.AuthResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Profile("!prod")
@RestController
@RequiredArgsConstructor
public class DevController {
    private final TokenGenerator tokenGenerator;

    @Operation(summary = "페이크 토큰 발행", description = "특정 회원의 개발용 페이크 인증 토큰을 발행합니다. 파라미터는 실제 회원 ID를 사용해야 합니다.")
    @PostMapping("/v1/auth/fake-token")
    AuthResultResponse generateFakeToken(
            @RequestParam String memberId
    ) {
        TokenPair tokenPair = tokenGenerator.generateTokenPair(memberId);
        return AuthResultResponse.of(tokenPair, false);
    }
}
