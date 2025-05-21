package com.noair.easip.house.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "집세")
public record RentDto(
        @Schema(description = "보증금", example = "100000000")
        Long deposit,

        @Schema(description = "월세", example = "500000")
        Long monthlyRent
) {
}
