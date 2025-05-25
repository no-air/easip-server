package com.noair.easip.post.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "임대조건")
public record RoomRentalConditionResponse(
    @Schema(description = "공급유형", example = "청년")
    String supplyType,

    @Schema(description = "주거유형", example = "A타입 특별공급")
    String livingType,

    @Schema(description = "전용면적", example = "36.4")
    Double exclusiveArea,

    @Schema(description = "공급호수", example = "100")
    Integer supplyRoomCount,

    @Schema(description = "최소 보증 비율 보증금", example = "100000000")
    Double minRatioDeposit,

    @Schema(description = "최소 보증 비율 월세", example = "100000000")
    Double minRatioMonthlyRent,

    @Schema(description = "최대 보증 비율 보증금", example = "100000000")
    Double maxRatioDeposit,

    @Schema(description = "최대 보증 비율 월세", example = "100000000")
    Double maxRatioMonthlyRent
) {
    public static RoomRentalConditionResponse of(
        String supplyType,
        String livingType,
        Double exclusiveArea,
        Integer supplyRoomCount,
        Double minRatioDeposit,
        Double minRatioMonthlyRent,
        Double maxRatioDeposit,
        Double maxRatioMonthlyRent
    ) {
        return new RoomRentalConditionResponse(
            supplyType,
            livingType,
            exclusiveArea,
            supplyRoomCount,
            minRatioDeposit,
            minRatioMonthlyRent,
            maxRatioDeposit,
            maxRatioMonthlyRent
        );  
    }
}
