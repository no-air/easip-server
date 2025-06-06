package com.noair.easip.post.controller.dto;

import com.querydsl.core.annotations.QueryProjection;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "임대조건")
public record PostHouseConditionDto(
    @Schema(description = "공급유형", example = "청년")
    String supplyType,

    @Schema(description = "주거유형", example = "A타입 특별공급")
    String livingType,

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
    public static PostHouseConditionDto of(
        String supplyType,
        String livingType,
        Integer supplyRoomCount,
        Double minRatioDeposit,
        Double minRatioMonthlyRent,
        Double maxRatioDeposit,
        Double maxRatioMonthlyRent
    ) {
        return new PostHouseConditionDto(
            supplyType,
            livingType,
            supplyRoomCount,
            minRatioDeposit,
            minRatioMonthlyRent,
            maxRatioDeposit,
            maxRatioMonthlyRent
        );  
    }

    @QueryProjection
    public PostHouseConditionDto(
            String supplyType,
            String livingType,
            Integer supplyRoomCount,
            Double minRatioDeposit,
            Double minRatioMonthlyRent,
            Double maxRatioDeposit,
            Double maxRatioMonthlyRent
    ) {
        this.supplyType = supplyType;
        this.livingType = livingType;
        this.supplyRoomCount = supplyRoomCount;
        this.minRatioDeposit = minRatioDeposit;
        this.minRatioMonthlyRent = minRatioMonthlyRent;
        this.maxRatioDeposit = maxRatioDeposit;
        this.maxRatioMonthlyRent = maxRatioMonthlyRent;
    }
}
