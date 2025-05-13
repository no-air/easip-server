package com.noair.easip.member.controller;

import com.noair.easip.auth.controller.LoginMemberId;
import com.noair.easip.member.controller.dto.response.MemberResponse;
import com.noair.easip.member.domain.Position;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "개인 정보 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/me")
@Valid
public class MeController {

    @Operation(summary = "내 프로필 조회")
    @GetMapping("/profile")
    MemberResponse getMyProfile(
            @Parameter(hidden = true)
            @LoginMemberId
            String loginMemberId
    ) {
        return MemberResponse.of(
                loginMemberId,
                "나나미",
                30,
                "42",
                List.of("강서구", "영등포구"),
                "영등포구",
                false,
                Position.YOUNG_MAN,
                1,
                5000000L,
                30000000L,
                150000000L
        );
    }
}
