package com.noair.easip.member.controller;

import com.noair.easip.auth.controller.LoginMemberId;
import com.noair.easip.house.domain.District;
import com.noair.easip.member.controller.dto.request.CreateMemberRequest;
import com.noair.easip.member.controller.dto.response.MemberResponse;
import com.noair.easip.member.domain.Member;
import com.noair.easip.member.domain.Position;
import com.noair.easip.member.service.MemberDeviceService;
import com.noair.easip.member.service.MemberService;
import com.noair.easip.post.controller.dto.PostElementResponse;
import com.noair.easip.post.service.PostScheduleService;
import com.noair.easip.post.service.PostService;
import com.noair.easip.util.ArrayResponse;
import com.noair.easip.util.DefaultResponse;
import com.noair.easip.util.PaginationDto;
import com.noair.easip.util.PaginationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "개인 정보 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/me")
@Validated
public class MeController {
    private final MemberService memberService;
    private final PostScheduleService postScheduleService;
    private final PostService postService;
    private final MemberDeviceService memberDeviceService;

    @Operation(summary = "내 프로필 조회")
    @GetMapping("/profile")
    MemberResponse getMyProfile(
            @Parameter(hidden = true)
            @LoginMemberId
            String loginMemberId
    ) {
        Member member = memberService.getMemberById(loginMemberId);
        int pushAlarmRegisteredPostCount = postScheduleService.getPushAlarmRegisteredPostCount(loginMemberId);

        return MemberResponse.of(
                member.getName(),
                pushAlarmRegisteredPostCount,
                member.getDateOfBirth(),
                member.getLikingDistricts().stream().map(District::getName).toList(),
                member.getLivingDistrict().getName(),
                member.getMyMonthlySalary().longValue(),
                member.getFamilyMemberMonthlySalary().longValue(),
                member.getAllFamilyMemberCount(),
                member.getPosition(),
                member.getHasCar(),
                member.getCarPrice(),
                member.getAssetPrice().longValue()
        );
    }

    @Operation(summary = "내 프로필 수정")
    @PutMapping("/profile")
    DefaultResponse updateMyProfile(
            @Parameter(hidden = true)
            @LoginMemberId
            String loginMemberId,

            @RequestBody
            CreateMemberRequest request
    ) {

        memberService.updateMember(loginMemberId, request);
        return DefaultResponse.ok();
    }

    @Operation(summary = "회원 탈퇴")
    @DeleteMapping
    DefaultResponse deleteMyProfile(
            @Parameter(hidden = true)
            @LoginMemberId
            String loginMemberId
    ) {
        memberService.deleteMemberSoft(loginMemberId);
        return DefaultResponse.ok();
    }

    @Operation(summary = "관심 공고 목록 조회", description = "알림을 등록한 공고를 조회한다.")
    @GetMapping("/like/posts")
    PaginationResponse<PostElementResponse> fetchLikingPostList(
            @Parameter(description = "검색 키워드", example = "서울")
            @RequestParam(required = false, defaultValue = "")
            String keyword,

            @Parameter(description = "가져올 현재 페이지", example = "1")
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
        PaginationDto<PostElementResponse> fetchResult = postService.fetchLikingPostList(keyword, loginMemberId, page, size);

        return PaginationResponse
                .of(fetchResult, page, size);
    }

    @Operation(summary = "FCM 토큰 등록 및 갱신")
    @PostMapping("/fcm-token")
    DefaultResponse registerFcmToken(
            @RequestParam
            String fcmToken,

            @Parameter(hidden = true)
            @LoginMemberId
            String loginMemberId
    ) {
        Member member = memberService.getMemberById(loginMemberId);
        memberDeviceService.registerFcmToken(fcmToken, member);

        return DefaultResponse.ok();
    }
}
