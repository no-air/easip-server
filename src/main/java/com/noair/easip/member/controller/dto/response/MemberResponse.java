package com.noair.easip.member.controller.dto.response;

import com.noair.easip.member.domain.Position;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.List;

@Schema(name = "MemberResponse", description = "회원 정보")
public record MemberResponse(
        @Schema(description = "회원 이름", example = "나나미")
        String name,

        @Schema(description = "관심 공고 수", example = "2")
        Integer likingPostCount,

        @Schema(description = "생년월일 yyyy-MM-dd", example = "2001-01-25")
        LocalDate dayOfBirth,

        @Schema(description = "선호 자치구 목록", example = "[\"강서구\",\"영등포구\"]")
        List<String> likingDistrictIds,

        @Schema(description = "거주 자치구", example = "영등포구")
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

        @Schema(description = "총자산가액 (KRW)", example = "150000000")
        Long assetPrice
) {
        public static MemberResponse of(
                String name,
                Integer likingPostCount,
                LocalDate dayOfBirth,
                List<String> likingCityIds,
                String livingCityId,
                Long myMonthlySalary,
                Long familyMemberMonthlySalary,
                Integer allFamilyMemberCount,
                Position position,
                Boolean hasCar,
                Long carPrice,
                Long assetPrice) {
        return new MemberResponse(
                        name,
                        likingPostCount,
                        dayOfBirth,
                        likingCityIds,
                        livingCityId,
                        myMonthlySalary,
                        familyMemberMonthlySalary,
                        allFamilyMemberCount,
                        position,
                        hasCar,
                        carPrice,
                        assetPrice);
        }
}
