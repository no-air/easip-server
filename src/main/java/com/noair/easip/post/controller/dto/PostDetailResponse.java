package com.noair.easip.post.controller.dto;

import java.util.List;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "공고 상세 정보")
public record PostDetailResponse(
    @Schema(description = "공고 ID", example = "01HGW2N7EHJVJ4CJ999RRS2E97")
    String postId,

    @Schema(description = "공고 제목", example = "신당역 신당큐브스테이트오피스텔 추가모집공고")
    String postTitle,

    @Schema(description = "공고 태그", example = "강서구, 민감임대, 청약중")
    List<String> tags,

    @Schema(description = "주택별 공고 상세 정보")
    List<PostPerHouseDetailDto> postPerHouseDetailResponses
) {
    public static PostDetailResponse of(
        String postId,
        String postTitle,
        List<String> tags,
        List<PostPerHouseDetailDto> postPerHouseDetailResponses
    ) {
        return new PostDetailResponse(
            postId,
            postTitle,
            tags,
                postPerHouseDetailResponses
        );
    }
}
