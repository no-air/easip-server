package com.noair.easip.post.controller;

import com.noair.easip.house.controller.dto.HouseSummaryResponse;
import com.noair.easip.house.controller.dto.RentDto;
import com.noair.easip.post.controller.dto.ApplicationConditionDto;
import com.noair.easip.post.controller.dto.PostElementResponse;
import com.noair.easip.post.controller.dto.PostSummaryResponse;
import com.noair.easip.post.controller.dto.ScheduleDto;
import com.noair.easip.util.PaginationDto;
import com.noair.easip.util.PaginationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "공고 API")
@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/v1/posts")
public class PostController {

    @Operation(summary = "홈 페이지 공고 조회")
    @GetMapping("/home")
    PaginationResponse<PostSummaryResponse> getHomePosts(
            @Parameter(description = "가져올 현재 페이지", example = "1")
            @RequestParam(required = false, defaultValue = "1")
            @Min(value = 1)
            Integer page,

            @Parameter(description = "페이지당 아이템 수", example = "10")
            @RequestParam(required = false, defaultValue = "10")
            @Min(value = 1)
            Integer size
    ) {
        return PaginationResponse.of(
            PaginationDto.of(
                999,
                List.of(
                    PostSummaryResponse.of(
                        "01HGW2N7EHJVJ4CJ999RRS2E97",
                        "[민간임대] 신당역 신당큐브스테이트오피스텔 추가모집공고",
                        List.of(new ApplicationConditionDto("청년", true), new ApplicationConditionDto("신혼부부", false), new ApplicationConditionDto("개인무주택", true), new ApplicationConditionDto("소득기준 2억원 이내(1인가구 기준)", true)),
                        List.of(new HouseSummaryResponse("01HGW2N7EHJVJ4CJ999RRS2E97", "https://soco.seoul.go.kr/cohome/cmmn/file/fileDown.do?atchFileId=356f0ab9e20e42f88b4c06e1c18e46ad&fileSn=12", "신당역 신당큐브스테이트오피스텔", "청약예정", "중구")),
                        List.of(new RentDto(100000000L, 1000000L), new RentDto(150000000L, 900000L), new RentDto(200000000L, 800000L)),
                        List.of(new ScheduleDto("모집공고", "2025-05-07", null), new ScheduleDto("청약신청", "2025-05-12T09:00:00", "2025-05-18T24:00:00"), new ScheduleDto("예비번호 발표", "2025-05-19T17:00:00", null), new ScheduleDto("계약체결", "공실 발생시 순차적으로 연락", null))
                    ),
                    PostSummaryResponse.of(
                        "01HGW2N7EHJVJ4CJ999RRS2E97",
                        "[민간임대] 신당역 신당큐브스테이트오피스텔 추가모집공고",
                        List.of(new ApplicationConditionDto("청년", true), new ApplicationConditionDto("신혼부부", false), new ApplicationConditionDto("개인무주택", true), new ApplicationConditionDto("소득기준 2억원 이내(1인가구 기준)", true)),
                        List.of(new HouseSummaryResponse("01HGW2N7EHJVJ4CJ999RRS2E97", "https://soco.seoul.go.kr/cohome/cmmn/file/fileDown.do?atchFileId=356f0ab9e20e42f88b4c06e1c18e46ad&fileSn=12", "신당역 신당큐브스테이트오피스텔", "청약예정", "중구")),
                        List.of(new RentDto(100000000L, 1000000L), new RentDto(150000000L, 900000L), new RentDto(200000000L, 800000L)),
                        List.of(new ScheduleDto("모집공고", "2025-05-07", null), new ScheduleDto("청약신청", "2025-05-12T09:00:00", "2025-05-18T24:00:00"), new ScheduleDto("예비번호 발표", "2025-05-19T17:00:00", null), new ScheduleDto("계약체결", "공실 발생시 순차적으로 연락", null))
                    ),
                    PostSummaryResponse.of(
                        "01HGW2N7EHJVJ4CJ999RRS2E97",
                        "[민간임대] 신당역 신당큐브스테이트오피스텔 추가모집공고",
                        List.of(new ApplicationConditionDto("청년", true), new ApplicationConditionDto("신혼부부", false), new ApplicationConditionDto("개인무주택", true), new ApplicationConditionDto("소득기준 2억원 이내(1인가구 기준)", true)),
                        List.of(new HouseSummaryResponse("01HGW2N7EHJVJ4CJ999RRS2E97", "https://soco.seoul.go.kr/cohome/cmmn/file/fileDown.do?atchFileId=356f0ab9e20e42f88b4c06e1c18e46ad&fileSn=12", "신당역 신당큐브스테이트오피스텔", "청약예정", "중구")),
                        List.of(new RentDto(100000000L, 1000000L), new RentDto(150000000L, 900000L), new RentDto(200000000L, 800000L)),
                        List.of(new ScheduleDto("모집공고", "2025-05-07", null), new ScheduleDto("청약신청", "2025-05-12T09:00:00", "2025-05-18T24:00:00"), new ScheduleDto("예비번호 발표", "2025-05-19T17:00:00", null), new ScheduleDto("계약체결", "공실 발생시 순차적으로 연락", null))
                    )
                )
            ),
            page,
            size
        );
    }

    @Operation(summary = "공고 목록 조회")
    @GetMapping("/list")
    PaginationResponse<PostElementResponse> getPostList(
            @Parameter(description = "가져올 현재 페이지", example = "1")
            @RequestParam(required = false, defaultValue = "1")
            @Min(value = 1)
            Integer page,

            @Parameter(description = "페이지당 아이템 수", example = "10")
            @RequestParam(required = false, defaultValue = "10")
            @Min(value = 1)
            Integer size
    ) {
        return PaginationResponse.of(
            PaginationDto.of(
                    999,
                    List.of(
                            PostElementResponse.of(
                                    "01HGW2N7EHJVJ4CJ999RRS2E97",
                                    "[민간임대] 신당역 신당큐브스테이트오피스텔 추가모집공고",
                                    "2025-05-12T09:00:00",
                                    "2025-05-18T24:00:00",
                                    100
                            )
                    )
            ),
            page,
            size
        );
    }
}
