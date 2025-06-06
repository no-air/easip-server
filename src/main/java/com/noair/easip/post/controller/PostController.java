package com.noair.easip.post.controller;

import com.noair.easip.auth.controller.LoginMemberId;
import com.noair.easip.post.controller.dto.*;
import com.noair.easip.post.domain.Post;
import com.noair.easip.post.service.PostScheduleService;
import com.noair.easip.post.service.PostService;
import com.noair.easip.util.ArrayResponse;
import com.noair.easip.util.DefaultResponse;
import com.noair.easip.util.PaginationDto;
import com.noair.easip.util.PaginationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "공고 API")
@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/v1/posts")
public class PostController {
    private final PostService postService;
    private final PostScheduleService postScheduleService;

    @Operation(summary = "홈 페이지 공고 조회")
    @GetMapping("/home")
    PaginationResponse<PostSummaryResponse> fetchHomePosts(
            @Parameter(description = "가져올 현재 페이지 (1부터 시작)", example = "1")
            @RequestParam(required = false, defaultValue = "1")
            @Min(value = 1)
            Integer page,

            @Parameter(description = "페이지당 아이템 수", example = "10")
            @RequestParam(required = false, defaultValue = "10")
            @Min(value = 1)
            Integer size,

            @Parameter(hidden = true)
            @LoginMemberId
            String loginMemberId
    ) {
        PaginationDto<PostSummaryResponse> fetchResult = postService.fetchHomePosts(page, size, loginMemberId);

        return PaginationResponse
                .of(fetchResult, page, size);
    }

    @Operation(summary = "공고 목록 조회")
    @GetMapping("/list/{keyword}")
    PaginationResponse<PostElementResponse> fetchPostList(
            @Parameter(description = "검색 키워드", example = "서울")
            @PathVariable(name = "keyword")
            String keyword,

            @Parameter(description = "가져올 현재 페이지", example = "1")
            @RequestParam(required = false, defaultValue = "1")
            @Min(value = 1)
            Integer page,

            @Parameter(description = "페이지당 아이템 수", example = "10")
            @RequestParam(required = false, defaultValue = "10")
            @Min(value = 1)
            Integer size
    ) {
        PaginationDto<PostElementResponse> fetchResult = postService.fetchPostList(keyword, page, size);

        return PaginationResponse
                .of(fetchResult, page, size);
    }

    @Operation(summary = "공고 상세 조회")
    @GetMapping("/{postId}")
    PostDetailResponse getPostDetail(
            @Parameter(description = "공고 ID", example = "01HGW2N7EHJVJ4CJ999RRS2E97")
            @PathVariable("postId")
            String postId,

            @Parameter(hidden = true)
            @LoginMemberId
            String loginMemberId
    ) {
        Post post = postService.getPostById(postId);
        List<PostPerHouseDetailDto> postPerHouseDetailDtos = postService.getPostPerHouseDetails(postId, loginMemberId);

        return PostDetailResponse.of(
                post.getId(),
                post.getTitle(),
                post.getBadgeNames(),
                postPerHouseDetailDtos
        );
    }

    @Operation(summary = "공고일정별 푸시알림 목록 조회")
    @GetMapping("/{postId}/push/schedules")
    ArrayResponse<ScheduleDto> getPostSchedulePushAlarms(
            @Parameter(description = "공고 ID", example = "01HGW2N7EHJVJ4CJ999RRS2E97")
            @PathVariable
            String postId,

            @Parameter(hidden = true)
            @LoginMemberId
            String loginMemberId
    ) {
        Post post = postService.getPostById(postId);

        return ArrayResponse.of(
                postScheduleService.getScheduleDtoByPost(post, loginMemberId)
        );
    }

    @Operation(summary = "공고일정별 푸시알림 등록 토글")
    @PutMapping("/{postId}/push/schedules/{scheduleId}")
    DefaultResponse togglePostSchedulePushAlarm(
            @Parameter(description = "공고 ID", example = "01HGW2N7EHJVJ4CJ999RRS2E97")
            @PathVariable
            String postId,

            @Parameter(description = "일정 ID", example = "01HGW2N7EHJVJ4CJ999RRS2E97")
            @PathVariable
            String scheduleId,

            @Parameter(hidden = true)
            @LoginMemberId
            String loginMemberId
    ) {
        postScheduleService.toggleScheduleNotification(scheduleId, loginMemberId);
        return DefaultResponse.ok();
    }
}
