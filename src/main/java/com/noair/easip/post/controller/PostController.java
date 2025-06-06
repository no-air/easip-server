package com.noair.easip.post.controller;

import com.noair.easip.auth.controller.LoginMemberId;
import com.noair.easip.house.domain.Badge;
import com.noair.easip.post.controller.dto.PostDetailResponse;
import com.noair.easip.post.controller.dto.PostElementResponse;
import com.noair.easip.post.controller.dto.PostPerHouseDetailDto;
import com.noair.easip.post.controller.dto.PostSummaryResponse;
import com.noair.easip.post.controller.dto.ScheduleDto;
import com.noair.easip.post.domain.Post;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;

@Tag(name = "공고 API")
@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/v1/posts")
public class PostController {
    private final PostService postService;

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
    @GetMapping("/list")
    PaginationResponse<PostElementResponse> fetchPostList(
            @Parameter(description = "가져올 현재 페이지", example = "1")
            @RequestParam(required = false, defaultValue = "1")
            @Min(value = 1)
            Integer page,

            @Parameter(description = "페이지당 아이템 수", example = "10")
            @RequestParam(required = false, defaultValue = "10")
            @Min(value = 1)
            Integer size
    ) {
        PaginationDto<PostElementResponse> fetchResult = postService.fetchPostList(page, size);

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

    @Operation(summary = "[MOCK: 랜덤값] 공고일정별 푸시알림 목록 조회")
    @GetMapping("/{postId}/push/schedules")
    ArrayResponse<ScheduleDto> getPostSchedulePushAlarms(
            @Parameter(description = "공고 ID", example = "01HGW2N7EHJVJ4CJ999RRS2E97")
            @PathVariable
            String postId
    ) {
        return ArrayResponse.of(List.of(
                ScheduleDto.of("01HGW2N7EHJVJ4CJ999RRS2E97", "모집공고", "2025-05-07", null, false),
                ScheduleDto.of("01HGW2N7EHJVJ4CJ999RRS2E98", "청약신청", "2025-05-12T09:00:00", "2025-05-18T24:00:00", false),
                ScheduleDto.of("01HGW2N7EHJVJ4CJ999RRS2E99", "예비번호 발표", "2025-05-19T17:00:00", null, false),
                ScheduleDto.of("01HGW2N7EHJVJ4CJ999RRS2E10", "계약체결", "공실 발생시 순차적으로 연락", null, false)
        ));
    }

    @Operation(summary = "[MOCK: 랜덤값] 공고일정별 푸시알림 등록 토글")
    @PutMapping("/{postId}/push/schedules/{scheduleId}")
    DefaultResponse togglePostSchedulePushAlarm(
            @Parameter(description = "공고 ID", example = "01HGW2N7EHJVJ4CJ999RRS2E97")
            @PathVariable
            String postId,

            @Parameter(description = "일정 ID", example = "01HGW2N7EHJVJ4CJ999RRS2E97")
            @PathVariable
            String scheduleId
    ) {
        return new Random().nextBoolean() ? DefaultResponse.ok() : DefaultResponse.fail();
    }
}
