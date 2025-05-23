package com.noair.easip.house.controller.dto;

import java.util.List;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "주택 상세 정보")
public record HouseDetailResponse(
    @Schema(description = "주택 ID", example = "01HGW2N7EHJVJ4CJ999RRS2E97")
    String houseId,

    @Schema(description = "주택 이름", example = "신당역 신당큐브스테이트오피스텔")
    String houseName,

    @Schema(description = "주택 태그", example = "강서구, 민감임대, 청약중")
    List<String> tags,

    @Schema(description = "주택 사진 URL")
    List<String> houseThumbnailUrl,

    @Schema(description = "주택 주소", example = "서울특별시 중구 신당동 123-45")
    String houseAddress,

    @Schema(description = "인근 지하철역", example = "신당역 2호선, 3호선")
    String nearSubwayStation,

    @Schema(description = "시행사 명", example = "더클래식동작")
    String developerName,

    @Schema(description = "시공사 명", example = "(주)강남건영)")
    String constructorName,

    @Schema(description = "최초모집공고일", example = "2025-01-01")
    String firstRecruitmentDate,

    @Schema(description = "입주(예정)일", example = "2025-02-01")
    String moveInDate,

    @Schema(description = "일반공급 수", example = "100")
    Integer generalSupplyCount,

    @Schema(description = "일반공급 세대별 정보")
    List<RoomInfoResponse> generalSupplyRoomInfos,

    @Schema(description = "특별공급 수", example = "100")
    Integer specialSupplyCount,

    @Schema(description = "특별공급 세대별 정보")
    List<RoomInfoResponse> specialSupplyRoomInfos,

    @Schema(description = "배치도 사진 url 목록", example = "https://soco.seoul.go.kr/cohome/cmmn/file/fileDown.do?atchFileId=356f0ab9e20e42f88b4c06e1c18e46ad&fileSn=12")
    List<String> floorPlanPictures,

    @Schema(description = "주택 위도", example = "37.5665")
    Double latitude,

    @Schema(description = "주택 경도", example = "126.978")
    Double longitude
) {
    public static HouseDetailResponse of(
        String houseId,
        String houseName,
        List<String> tags,
        List<String> houseThumbnailUrl,
        String houseAddress,
        String nearSubwayStation,
        String developerName,
        String constructorName,
        String firstRecruitmentDate,
        String moveInDate,
        Integer generalSupplyCount,
        List<RoomInfoResponse> generalSupplyRoomInfos,
        Integer specialSupplyCount,
        List<RoomInfoResponse> specialSupplyRoomInfos,
        List<String> floorPlanPictures,
        Double latitude,
        Double longitude
    ) {
        return new HouseDetailResponse(
            houseId,
            houseName,
            tags,
            houseThumbnailUrl,
            houseAddress,
            nearSubwayStation,
            developerName,
            constructorName,
            firstRecruitmentDate,
            moveInDate,
            generalSupplyCount,
            generalSupplyRoomInfos,
            specialSupplyCount,
            specialSupplyRoomInfos,
            floorPlanPictures,
            latitude,
            longitude
        );
    }
}
