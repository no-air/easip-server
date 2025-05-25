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

import java.time.LocalDate;
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
            @Parameter(hidden = true) @LoginMemberId String loginMemberId) {
        return MemberResponse.of(
                "나나미",
                LocalDate.of(2001, 1, 25),
                List.of("01HGW2N7EHJVJ4CJ999RRS2E", "01HGW2N7EHJVJ4CJ999RRS2E"),
                "01HGW2N7EHJVJ4CJ999RRS2E",
                5000000L,
                5000000L,
                4,
                Position.YOUNG_MAN,
                false,
                30000000L,
                150000000L);
    }
}
