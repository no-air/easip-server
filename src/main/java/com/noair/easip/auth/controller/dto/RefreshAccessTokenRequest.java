package com.noair.easip.auth.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "기존 엑세스 토큰 재발행 요청")
public record RefreshAccessTokenRequest(
        @NotBlank @Schema(description = "기존 리프레시 토큰", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyIdI6MjEwMCwiaWF0IjoxNjM4NjM2NDI3fQ.5zBBo9LMi9Y_L6gyN0WYq41Qn2GJGSySMs7XJ6c_aFk")
        String refreshToken
) {
}
