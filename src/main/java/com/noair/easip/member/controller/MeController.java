package com.noair.easip.member.controller;

import com.noair.easip.auth.controller.LoginMemberId;
import com.noair.easip.house.domain.District;
import com.noair.easip.member.controller.dto.request.CreateMemberRequest;
import com.noair.easip.member.controller.dto.response.MemberResponse;
import com.noair.easip.member.domain.Member;
import com.noair.easip.member.domain.Position;
import com.noair.easip.member.service.MemberService;
import com.noair.easip.post.service.PostScheduleService;
import com.noair.easip.util.DefaultResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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


}
