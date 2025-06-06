package com.noair.easip.house.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "주택 리스트 항목")
public record HouseElementResponse(
        @Schema(description = "주택 ID", example = "01HGW2N7EHJVJ4CJ999RRS2E97")
        String houseId,

        @Schema(description = "주택 사진 URL")
        String houseThumbnailUrl,

        @Schema(description = "주택 이름", example = "신당역 신당큐브스테이트오피스텔")
        String houseName,

        @Schema(description = "청약 상태", example = "청약예정")
        String subscriptionState,

        @Schema(description = "구", example = "중구")
        String districtName,

        @Schema(description = "위도", example = "37.5665")
        Double latitude,

        @Schema(description = "경도", example = "126.978")
        Double longitude
) {
    public static HouseElementResponse of(String houseId,
                                          String houseThumbnailUrl,
                                          String houseName,
                                          String subscriptionState,
                                          String districtName,
                                          Double latitude,
                                          Double longitude) {
        return new HouseElementResponse(houseId,
                houseThumbnailUrl,
                houseName,
                subscriptionState,
                districtName,
                latitude,
                longitude
        );
    }
}
