package com.noair.easip.member.controller.dto.request;

import com.noair.easip.member.domain.Position;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.List;

@Schema(description = "사용자 회원가입 요청")
public record CreateNewMemberRequest(
        @NotBlank
        @Size(min = 1)
        @Schema(description = "회원 이름", example = "나나미")
        String name,

        @NotNull
        @Schema(description = "생년월일 yyyy-MM-dd", example = "2001-01-25")
        LocalDate dayOfBirth,

        @NotNull
        @Size(min = 1)
        @Schema(description = "회원 나이", example = "30")
        Integer age,

        @Schema(description = "선호 도시 ID목록", example = "[\"01HGW2N7EHJVJ4CJ999RRS2E\",\"01HGW2N7EHJVJ4CJ999RRS2E\"]")
        List<String> likingCityIds,

        @NotBlank
        @Schema(description = "거주 도시 ID", example = "01HGW2N7EHJVJ4CJ999RRS2E")
        String livingCityId,

        @NotNull
        @Schema(description = "주택 보유 여부", example = "false")
        Boolean hasHouse,

        @NotNull
        @Schema(description = "신분", example = "YOUNG_MAN")
        Position position,

        @NotNull
        @Size(min = 1)
        @Schema(description = "세대원 수", example = "4")
        Integer familyMemberCount,

        @NotNull
        @Size(min = 1)
        @Schema(description = "월소득 (KRW)", example = "5000000")
        Long monthlySalary,

        @NotNull
        @Size(min = 1)
        @Schema(description = "자동차가액 (KRW)", example = "30000000")
        Long carPrice,

        @NotNull
        @Size(min = 1)
        @Schema(description = "총자산가액 (KRW)", example = "150000000")
        Long assetPrice
) {
}
