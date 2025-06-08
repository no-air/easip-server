package com.noair.easip.house.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "방 정보")
public record RoomInfoResponse(
    @Schema(description = "방 타입", example = "원룸")
    String type,

    @Schema(description = "공급유형", example = "17A")
    String exclusiveArea,

    @Schema(description = "신청자격", example = "청년")
    String applicationEligibility,

    @Schema(description = "전체세대수", example = "100")
    Integer totalRoomCount,

    @Schema(description = "보증금", example = "100000000")
    Double deposit,

    @Schema(description = "월세", example = "100000000")
    Double monthlyRent,

    @Schema(description = "(예상)관리비", example = "100000000")
    Double maintenanceFee
) {
    public static RoomInfoResponse of(
        String type,
        String exclusiveArea,
        String applicationEligibility,
        Integer totalRoomCount,
        Double deposit,
        Double monthlyRent,
        Double maintenanceFee
    ) {
        return new RoomInfoResponse(
            type,
            exclusiveArea,
            applicationEligibility,
            totalRoomCount,
            deposit,
            monthlyRent,
            maintenanceFee
        );
    }
}
