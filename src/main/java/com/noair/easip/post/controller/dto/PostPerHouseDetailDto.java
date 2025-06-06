package com.noair.easip.post.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "주택별 공고 상세 정보")
public record PostPerHouseDetailDto(
        @Schema(description = "주택 ID", example = "01HGW2N7EHJVJ4CJ999RRS2E97")
        String houseId,

        @Schema(description = "주택 이름", example = "신당역 신당큐브스테이트오피스텔")
        String houseName,

        @Schema(description = "공고 사진 URL", example = "https://travel.rakuten.co.jp/mytrip/sites/mytrip/files/migration_article_images/ranking/spot-fukuoka-fukuoka_02.jpg")
        String houseThumbnailUrl,

        @Schema(description = "공고 주소", example = "서울특별시 중구 신당동 123-45")
        String houseAddress,

        @Schema(description = "대표 보증금", example = "100000000")
        Double representativeDeposit,

        @Schema(description = "대표 월세", example = "100000000")
        Double representativeMonthlyRent,

        @Schema(description = "전체 공급호수", example = "100")
        Integer totalSupplyRoomCount,

        @Schema(description = "지원조건 목록", example = "[\"개인무주택\", \"소득기준 2억원 이내(1인가구 기준)\"]")
        List<ApplicationConditionDto> applicationConditionDtos,

        @Schema(description = "공급 일정 목록")
        List<ScheduleDto> scheduleDtos,

        @Schema(description = "타입별 임대조건 목록")
        List<PostHouseConditionDto> postHouseConditionDtos,

        @Schema(description = "주택 신청 URL", example = "https://www.google.com")
        String houseApplyUrl
) {
    public static PostPerHouseDetailDto of(
            String houseId,
            String houseName,
            String houseThumbnailUrl,
            String houseAddress,
            Double representativeDeposit,
            Double representativeMonthlyRent,
            Integer totalSupplyRoomCount,
            List<ApplicationConditionDto> applicationConditionDtos,
            List<ScheduleDto> scheduleDtos,
            List<PostHouseConditionDto> postHouseConditionDtos,
            String houseApplyUrl
    ) {
        return new PostPerHouseDetailDto(
                houseId,
                houseName,
                houseThumbnailUrl,
                houseAddress,
                representativeDeposit,
                representativeMonthlyRent,
                totalSupplyRoomCount,
                applicationConditionDtos,
                scheduleDtos,
                postHouseConditionDtos,
                houseApplyUrl
        );
    }
}
