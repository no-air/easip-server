package com.noair.easip.post.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "공고 리스트 항목")
public record PostElementResponse(
    @Schema(description = "공고 ID", example = "01HGW2N7EHJVJ4CJ999RRS2E97")
    String postId,

    @Schema(description = "공고 썸네일 이미지 URL", example = "https://example.com/thumbnail.jpg")
    String thumbnailUrl,

    @Schema(description = "공고 제목", example = "[민간임대] 신당역 신당큐브스테이트오피스텔 추가모집공고")
    String title,

    @Schema(description = "청약 상태", example = "청약예정")
    String subscriptionState,

    @Schema(description = "청약 시작일 (날짜 부분은 필수 값이지만, 시간 부분은 필수 값이 아님)", example = "2025-05-12T09:00:00")
    String applicationStart,

    @Schema(description = "청약 종료일 (날짜 부분은 필수 값이지만, 시간 부분은 필수 값이 아님)\n" +
                            "- 일정이 기간이 아닌 일시인 경우, end 값은 Null", example = "2025-05-18T24:00:00")
    String applicationEnd,

    @Schema(description = "모집호수", example = "100")
    Integer numberOfUnitsRecruiting
) {
    public static PostElementResponse of(String postId, String thumbnailUrl, String title, String subscriptionState, String applicationStart, String applicationEnd, Integer numberOfUnitsRecruiting) {
        return new PostElementResponse(postId, thumbnailUrl, title, subscriptionState, applicationStart, applicationEnd, numberOfUnitsRecruiting);
    }
}
