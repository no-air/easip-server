package com.noair.easip.auth.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "네이티브 소셜 로그인 요청")
public record NativeSocialLoginRequest(
        @NotBlank @Schema(description = "네이티브 로그인 절차 후 얻은 토큰", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyIdI6MjEwMCwiaWF0IjoxNjM4NjM2NDI3fQ.5zBBo9LMi9Y_L6gyN0WYq41Qn2GJGSySMs7XJ6c_aFk")
        String socialToken
) {
}
