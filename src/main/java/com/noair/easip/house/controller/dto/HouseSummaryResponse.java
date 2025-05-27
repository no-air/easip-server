package com.noair.easip.house.controller.dto;

import com.noair.easip.post.controller.dto.ApplicationConditionDto;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "주택 핵심 정보")
public record HouseSummaryResponse(
        @Schema(description = "주택 ID", example = "01HGW2N7EHJVJ4CJ999RRS2E97")
        String houseId,

        @Schema(description = "주택 사진 URL")
        String houseThumbnailUrl,

        @Schema(description = "주택 이름", example = "신당역 신당큐브스테이트오피스텔")
        String houseName,

        @Schema(description = "청약 상태", example = "청약예정")
        String subscriptionState,

        @Schema(description = "지원조건 목록", example = "[\"개인무주택\", \"소득기준 2억원 이내(1인가구 기준)\"]")
        List<ApplicationConditionDto> applicationConditionDtos,

        @Schema(description = "집세 목록")
        List<RentDto> rentDtos,

        @Schema(description = "구", example = "중구")
        String districtName,

        @Schema(description = "위도", example = "37.5665")
        Double latitude,

        @Schema(description = "경도", example = "126.978")
        Double longitude
) {
        public static HouseSummaryResponse of(
                String houseId,
                String houseThumbnailUrl,
                String houseName,
                String subscriptionState,
                List<ApplicationConditionDto> applicationConditionDtos,
                List<RentDto> rentDtos,
                String districtName,
                Double latitude,
                Double longitude
        ) {
                return new HouseSummaryResponse(houseId,
                        houseThumbnailUrl,
                        houseName,
                        subscriptionState,
                        applicationConditionDtos,
                        rentDtos,
                        districtName,
                        latitude,
                        longitude
                );
        }
}
