package com.noair.easip.member.controller.dto.response;

import com.noair.easip.member.domain.Position;
import com.noair.easip.member.domain.WorkType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.List;

@Schema(name = "MemberResponse", description = "회원 정보")
public record MemberResponse(
        @Schema(description = "회원 이름", example = "나나미")
        String name,

        @Schema(description = "근무 상태", example = "UNIVERSITY_STUDENT")
        WorkType workType,

        @Schema(description = "생년월일 yyyy-MM-dd", example = "2001-01-25")
        LocalDate dayOfBirth,

        @Schema(description = "선호구 ID목록", example = "[\"01HGW2N7EHJVJ4CJ999RRS2E\",\"01HGW2N7EHJVJ4CJ999RRS2E\"]")
        List<String> likingDistrictIds,

        @Schema(description = "거주구 ID", example = "01HGW2N7EHJVJ4CJ999RRS2E")
        String livingDistrictId,

        @Schema(description = "월소득 (KRW)", example = "5000000")
        Long myMonthlySalary,

        @Schema(description = "본인 제외 가족 월소득 (KRW)", example = "5000000")
        Long familyMemberMonthlySalary,

        @Schema(description = "본인 포함 세대원 수", example = "4")
        Integer allFamilyMemberCount,

        @Schema(description = "신분", example = "YOUNG_MAN")
        Position position,

        @Schema(description = "자동차 소유 여부", example = "false")
        Boolean hasCar,

        @Schema(description = "자동차가액 (KRW)", example = "30000000")
        Long carPrice,

        @Schema(description = "주택 보유 여부", example = "false")
        Boolean hasHouse,

        @Schema(description = "총자산가액 (KRW)", example = "150000000")
        Long assetPrice
) {
        public static MemberResponse of(
                        String name,
                        WorkType workType,
                        LocalDate dayOfBirth,
                        List<String> likingCityIds,
                        String livingCityId,
                        Long myMonthlySalary,
                        Long familyMemberMonthlySalary,
                        Integer allFamilyMemberCount,
                        Position position,
                        Boolean hasCar,
                        Long carPrice,
                        Boolean hasHouse,
                        Long assetPrice) {
                return new MemberResponse(
                                name,
                                workType,
                                dayOfBirth,
                                likingCityIds,
                                livingCityId,
                                myMonthlySalary,
                                familyMemberMonthlySalary,
                                allFamilyMemberCount,
                                position,
                                hasCar,
                                carPrice,
                                hasHouse,
                                assetPrice);
        }
}
