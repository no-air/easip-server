package com.noair.easip.member.controller.dto.request;

import com.noair.easip.member.domain.Position;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.List;

@Schema(description = "사용자 회원가입 요청")
public record CreateMemberRequest(
        @NotBlank
        @Size(min = 1)
        @Schema(description = "회원 이름", example = "나나미")
        String name,

        @NotNull
        @Schema(description = "생년월일 yyyy-MM-dd", example = "2001-01-25")
        LocalDate dayOfBirth,

        @Schema(description = "선호구 ID목록", example = "[\"01HGW2N7EHJVJ4CJ999RRS2E\",\"01HGW2N7EHJVJ4CJ999RRS2E\"]")
        List<String> likingDistrictIds,

        @NotBlank
        @Schema(description = "거주구 ID", example = "01HGW2N7EHJVJ4CJ999RRS2E")
        String livingDistrictId,

        @NotNull
        @Min(0)
        @Schema(description = "월소득 (KRW)", example = "5000000")
        Long myMonthlySalary,

        @NotNull
        @Min(0)
        @Schema(description = "본인 제외 가족 월소득 (KRW)", example = "5000000")
        Long familyMemberMonthlySalary,

        @NotNull
        @Min(1)
        @Schema(description = "본인 포함 세대원 수", example = "4")
        Integer allFamilyMemberCount,

        @NotNull
        @Schema(description = "신분", example = "YOUNG_MAN")
        Position position,

        @NotNull
        @Schema(description = "자동차 소유 여부", example = "false")
        Boolean hasCar,

        @NotNull
        @Min(0)
        @Schema(description = "자동차가액 (KRW)", example = "30000000")
        Long carPrice,

        @NotNull
        @Min(0)
        @Schema(description = "총자산가액 (KRW)", example = "150000000")
        Long assetPrice
) {
}
