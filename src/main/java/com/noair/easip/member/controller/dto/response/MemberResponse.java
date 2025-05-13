package com.noair.easip.member.controller.dto.response;

import com.noair.easip.member.domain.Position;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(name = "MemberResponse", description = "회원 응답 DTO")
public record MemberResponse(
        @Schema(description = "회원 ID", example = "01HGW2N7EHJVJ4CJ999RRS2E")
        String memberId,

        @Schema(description = "회원 이름", example = "나나미")
        String name,

        @Schema(description = "회원 나이", example = "30")
        Integer age,

        @Schema(description = "관심공고 수", example = "42")
        String likingPostCount,

        @Schema(description = "선호 도시 목록",
                example = "[\"강서구\",\"영등포구\"]")
        List<String> likingCityNames,

        @Schema(description = "거주 도시", example = "영등포구")
        String livingCityName,

        @Schema(description = "주택 보유 여부", example = "false")
        Boolean hasHouse,

        @Schema(description = "신분", example = "YOUNG_MAN")
        Position position,

        @Schema(description = "세대원 수", example = "4")
        Integer familyMemberCount,

        @Schema(description = "월소득 (KRW)", example = "5000000")
        Long monthlySalary,

        @Schema(description = "자동차가액 (KRW)", example = "30000000")
        Long carPrice,

        @Schema(description = "총자산가액 (KRW)", example = "150000000")
        Long assetPrice
) {
        public static MemberResponse of(
            String memberId,
            String name,
            String age,
            String likingPostCount,
            List<String> likingCityNames,
            String livingCityName,
            Boolean hasHouse,
            Position position,
            Integer familyMemberCount,
            Long monthlySalary,
            Long carPrice,
            Long assetPrice
    ) {
        return new MemberResponse(
                memberId,
                name,
                age,
                likingPostCount,
                likingCityNames,
                livingCityName,
                hasHouse,
                position,
                familyMemberCount,
                monthlySalary,
                carPrice,
                assetPrice
        );
    }
}
