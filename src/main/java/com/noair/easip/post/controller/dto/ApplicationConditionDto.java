package com.noair.easip.post.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "지원 조건 정보")
public record ApplicationConditionDto(
        @Schema(description = "지원 조건 내용", example = "개인무주택")
        String content,

        @Schema(description = "해당 여부", example = "true")
        boolean isApplicable
) {
}
