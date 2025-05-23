package com.noair.easip.post.controller.dto;

import java.util.List;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "주택별 공고 상세 정보")
public record PostPerHouseDetailResponse(
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

    @Schema(description = "대표 관리비", example = "100000000")
    Double representativeManagementFee,

    @Schema(description = "대표 구조", example = "원룸")
    String representativeStructure,

    @Schema(description = "대표 전용면적", example = "36.4")
    Double representativeExclusiveArea,

    @Schema(description = "전체 공급호수", example = "100")
    Integer totalSupplyRoomCount,

    @Schema(description = "지원조건 목록", example = "[\"개인무주택\", \"소득기준 2억원 이내(1인가구 기준)\"]")
    List<ApplicationConditionDto> applicationConditionDtos,

    @Schema(description = "공급 일정 목록")
    List<ScheduleDto> scheduleDtos,

    @Schema(description = "타입별 임대조건 목록")
    List<RoomRentalConditionResponse> roomRentalConditionResponses,

    @Schema(description = "주택 신청 URL", example = "https://www.google.com")
    String houseApplyUrl
) {
    public static PostPerHouseDetailResponse of(
        String houseId,
        String houseName,
        String houseThumbnailUrl,
        String houseAddress,
        Double representativeDeposit,
        Double representativeMonthlyRent,
        Double representativeManagementFee,
        String representativeStructure,
        Double representativeExclusiveArea,
        Integer totalSupplyRoomCount,
        List<ApplicationConditionDto> applicationConditionDtos,
        List<ScheduleDto> scheduleDtos,
        List<RoomRentalConditionResponse> roomRentalConditionResponses,
        String houseApplyUrl
    ) {
        return new PostPerHouseDetailResponse(
            houseId,
            houseName,
            houseThumbnailUrl,
            houseAddress,
            representativeDeposit,
            representativeMonthlyRent,
            representativeManagementFee,
            representativeStructure,
            representativeExclusiveArea,
            totalSupplyRoomCount,
            applicationConditionDtos,
            scheduleDtos,
            roomRentalConditionResponses,
            houseApplyUrl
        );
    }
}
