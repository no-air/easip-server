package com.noair.easip.house.controller.dto;

import com.noair.easip.house.domain.District;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "자치구")
public record DistrictDto(
        @Schema(description = "ID", example = "01HGW2N7EHJVJ4CJ999RRS2E97")
        String id,

        @Schema(description = "이름", example = "영등포구")
        String name
) {
    public static DistrictDto of(District district) {
        return new DistrictDto(
                district.getId(),
                district.getName()
        );
    }
}
