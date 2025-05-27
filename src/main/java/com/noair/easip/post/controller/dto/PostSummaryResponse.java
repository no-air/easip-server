package com.noair.easip.post.controller.dto;

import com.noair.easip.house.controller.dto.HouseSummaryResponse;
import com.noair.easip.house.controller.dto.RentDto;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "공고 핵심 정보")
public record PostSummaryResponse(
        @Schema(description = "공고 ID", example = "01HGW2N7EHJVJ4CJ999RRS2E97")
        String postId,

        @Schema(description = "공고 제목", example = "[민간임대] 신당역 신당큐브스테이트오피스텔 추가모집공고")
        String title,

        @Schema(description = "공고 태그", example = "강서구, 민감임대, 청약중")
        List<String> tags,

        @Schema(description = "주택 요약 정보")
        List<HouseSummaryResponse> houseSummaryResponses,

        @Schema(description = "공급 일정 목록")
        List<ScheduleDto> scheduleDtos
) {
    public static PostSummaryResponse of(
            String postId,
            String title,
            List<String> tags,
            List<HouseSummaryResponse> houseSummaryResponses,
            List<ScheduleDto> scheduleDtos
    ) {
        return new PostSummaryResponse(
                postId,
                title,
                tags,
                houseSummaryResponses,
                scheduleDtos
        );
    }
}
