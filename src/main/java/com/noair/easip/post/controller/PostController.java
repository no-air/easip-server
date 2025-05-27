package com.noair.easip.post.controller;

import com.noair.easip.house.controller.dto.HouseSummaryResponse;
import com.noair.easip.house.controller.dto.RentDto;
import com.noair.easip.post.controller.dto.ApplicationConditionDto;
import com.noair.easip.post.controller.dto.PostDetailResponse;
import com.noair.easip.post.controller.dto.PostElementResponse;
import com.noair.easip.post.controller.dto.PostPerHouseDetailResponse;
import com.noair.easip.post.controller.dto.PostSummaryResponse;
import com.noair.easip.post.controller.dto.RoomRentalConditionResponse;
import com.noair.easip.post.controller.dto.ScheduleDto;
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
import java.lang.reflect.Array;
import java.util.List;
import java.util.Random;

@Tag(name = "공고 API")
@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/v1/posts")
public class PostController {

    @Operation(summary = "[MOCK] 홈 페이지 공고 조회")
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
                        List.of("민간임대", "청약예정", "중구"),
                        List.of(new HouseSummaryResponse("01HGW2N7EHJVJ4CJ999RRS2E97", "https://soco.seoul.go.kr/cohome/cmmn/file/fileDown.do?atchFileId=356f0ab9e20e42f88b4c06e1c18e46ad&fileSn=12", "신당역 신당큐브스테이트오피스텔", "청약예정", List.of(new ApplicationConditionDto("청년", true), new ApplicationConditionDto("신혼부부", false), new ApplicationConditionDto("개인무주택", true), new ApplicationConditionDto("소득기준 2억원 이내(1인가구 기준)", true)), List.of(new RentDto(100000000.0, 1000000.0), new RentDto(150000000.0, 900000.0), new RentDto(200000000.0, 800000.0)), "중구", 37.5665, 126.978)),
                        List.of(new ScheduleDto("01HGW2N7EHJVJ4CJ999RRS2E97", "모집공고", "2025-05-07", null, false), new ScheduleDto("01HGW2N7EHJVJ4CJ999RRS2E98", "청약신청", "2025-05-12T09:00:00", "2025-05-18T24:00:00", false), new ScheduleDto("01HGW2N7EHJVJ4CJ999RRS2E99", "예비번호 발표", "2025-05-19T17:00:00", null, false), new ScheduleDto("01HGW2N7EHJVJ4CJ999RRS2E10", "계약체결", "공실 발생시 순차적으로 연락", null, false))
                    ),
                    PostSummaryResponse.of(
                        "01HGW2N7EHJVJ4CJ999RRS2E98",
                        "[민간임대] 영등포역 영등포큐브스테이트오피스텔 추가모집공고",
                        List.of("민간임대", "청약예정", "중구"),
                        List.of(new HouseSummaryResponse("01HGW2N7EHJVJ4CJ999RRS2E97", "https://soco.seoul.go.kr/cohome/cmmn/file/fileDown.do?atchFileId=356f0ab9e20e42f88b4c06e1c18e46ad&fileSn=12", "신당역 신당큐브스테이트오피스텔", "청약예정", List.of(new ApplicationConditionDto("청년", true), new ApplicationConditionDto("신혼부부", false), new ApplicationConditionDto("개인무주택", true), new ApplicationConditionDto("소득기준 2억원 이내(1인가구 기준)", true)), List.of(new RentDto(100000000.0, 1000000.0), new RentDto(150000000.0, 900000.0), new RentDto(200000000.0, 800000.0)), "중구", 37.5665, 126.978)),
                        List.of(new ScheduleDto("01HGW2N7EHJVJ4CJ999RRS2E11", "모집공고", "2025-05-07", null, false), new ScheduleDto("01HGW2N7EHJVJ4CJ999RRS2E33", "청약신청", "2025-05-12T09:00:00", "2025-05-18T24:00:00", false), new ScheduleDto("01HGW2N7EHJVJ4CJ999RRS2E99", "예비번호 발표", "2025-05-19T17:00:00", null, false), new ScheduleDto("01HGW2N7EHJVJ4CJ999RRS2E10", "계약체결", "공실 발생시 순차적으로 연락", null, false))
                    ),
                    PostSummaryResponse.of(
                        "01HGW2N7EHJVJ4CJ999RRS2E99",
                        "[민간임대] 마곡역 마곡큐브스테이트오피스텔 추가모집공고",
                        List.of("민간임대", "청약예정", "중구"),
                        List.of(new HouseSummaryResponse("01HGW2N7EHJVJ4CJ999RRS2E97", "https://soco.seoul.go.kr/cohome/cmmn/file/fileDown.do?atchFileId=356f0ab9e20e42f88b4c06e1c18e46ad&fileSn=12", "신당역 신당큐브스테이트오피스텔", "청약예정", List.of(new ApplicationConditionDto("청년", true), new ApplicationConditionDto("신혼부부", false), new ApplicationConditionDto("개인무주택", true), new ApplicationConditionDto("소득기준 2억원 이내(1인가구 기준)", true)), List.of(new RentDto(100000000.0, 1000000.0), new RentDto(150000000.0, 900000.0), new RentDto(200000000.0, 800000.0)), "중구", 37.5665, 126.978)),
                        List.of(new ScheduleDto("01HGW2N7EHJVJ4CJ999RRS2E22", "모집공고", "2025-05-07", null, false), new ScheduleDto("01HGW2N7EHJVJ4CJ999RRS2E44", "청약신청", "2025-05-12T09:00:00", "2025-05-18T24:00:00", false), new ScheduleDto("01HGW2N7EHJVJ4CJ999RRS2E99", "예비번호 발표", "2025-05-19T17:00:00", null, false), new ScheduleDto("01HGW2N7EHJVJ4CJ999RRS2E10", "계약체결", "공실 발생시 순차적으로 연락", null, false))
                    )
                )
            ),
            page,
            size
        );
    }

    @Operation(summary = "[MOCK] 공고 목록 조회")
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

    @Operation(summary = "[MOCK] 공고 상세 조회")
    @GetMapping("/{postId}")
    PostDetailResponse getPostDetail(
        @Parameter(description = "공고 ID", example = "01HGW2N7EHJVJ4CJ999RRS2E97")
        @PathVariable("postId") 
        String postId
    ) {
        return PostDetailResponse.of(
            "01HGW2N7EHJVJ4CJ999RRS2E97",
            "[민간임대] 신당역 신당큐브스테이트오피스텔 추가모집공고",
            List.of("민간임대", "청약예정", "중구"),
            List.of(
                PostPerHouseDetailResponse.of(
                    "01HGW2N7EHJVJ4CJ999RRS2E96",
                    "신당역 신당큐브스테이트오피스텔",
                    "https://travel.rakuten.co.jp/mytrip/sites/mytrip/files/migration_article_images/ranking/spot-fukuoka-fukuoka_02.jpg",
                    "서울특별시 중구 신당동 123-45",
                    100000000.0,
                    1000000.0,
                    150000.0,
                    "원룸",
                    52.23,
                    150,
                    List.of(
                        ApplicationConditionDto.of("청년", true),
                        ApplicationConditionDto.of("신혼부부", false),
                        ApplicationConditionDto.of("개인무주택", true),
                        ApplicationConditionDto.of("소득기준 2억원 이내(1인가구 기준)", true)
                    ),
                    List.of(
                        ScheduleDto.of("01HGW2N7EHJVJ4CJ999RRS2E97", "모집공고", "2025-05-07", null, false),
                        ScheduleDto.of("01HGW2N7EHJVJ4CJ999RRS2E98", "청약신청", "2025-05-12T09:00:00", "2025-05-18T24:00:00", false),
                        ScheduleDto.of("01HGW2N7EHJVJ4CJ999RRS2E99", "예비번호 발표", "2025-05-19T17:00:00", null, false),
                        ScheduleDto.of("01HGW2N7EHJVJ4CJ999RRS2E10", "계약체결", "공실 발생시 순차적으로 연락", null, false)
                    ),
                    List.of(
                        RoomRentalConditionResponse.of(
                            "청년",
                            "A타입 일반공급",
                            52.23,
                            50,
                            100000000.0,
                            1000000.0,
                            150000000.0,
                            1500000.0
                        ),
                        RoomRentalConditionResponse.of(
                            "청년",
                            "B타입 일반공급",
                            72.45,
                            50,
                            150000000.0,
                            1500000.0,
                            200000000.0,
                            2000000.0
                        ),
                        RoomRentalConditionResponse.of(
                            "청년,신혼부부",
                            "A타입 특별공급",
                            52.23,
                            25,
                            100000000.0,
                            1000000.0,
                            150000000.0,
                            1500000.0
                        ),
                        RoomRentalConditionResponse.of(
                            "청년,신혼부부",
                            "B타입 특별공급",
                            72.45,
                            25,
                            150000000.0,
                            1500000.0,
                            200000000.0,
                            2000000.0
                        )
                    ),
                    "https://www.google.com"
                ),
                PostPerHouseDetailResponse.of(
                    "01HGW2N7EHJVJ4CJ999RRS2E91",
                    "영등포역 영등포큐브스테이트오피스텔",
                    "https://travel.rakuten.co.jp/mytrip/sites/mytrip/files/migration_article_images/ranking/spot-fukuoka-fukuoka_02.jpg",
                    "서울특별시 영등포구 여의도동 123-45",
                    10000000.0,
                    100000.0,
                    15000.0,
                    "원룸",
                    54.23,
                    15,
                    List.of(
                        ApplicationConditionDto.of("청년", true),
                        ApplicationConditionDto.of("소득기준 2억원 이내(1인가구 기준)", true)
                    ),
                    List.of(
                        ScheduleDto.of("01HGW2N7EHJVJ4CJ999RRS2E97", "모집공고", "2025-06-07", null, false),
                        ScheduleDto.of("01HGW2N7EHJVJ4CJ999RRS2E98", "청약신청", "2025-06-12T09:00:00", "2025-06-18T24:00:00", false),
                        ScheduleDto.of("01HGW2N7EHJVJ4CJ999RRS2E99", "예비번호 발표", "2025-06-19T17:00:00", null, false),
                        ScheduleDto.of("01HGW2N7EHJVJ4CJ999RRS2E10", "계약체결", "2025-06-20T17:00:00", null, false)
                    ),
                    List.of(
                        RoomRentalConditionResponse.of(
                            "청년",
                            "A타입 일반공급",
                            54.23,
                            50,
                            10000000.0,
                            100000.0,
                            15000000.0,
                            150000.0
                        ),
                        RoomRentalConditionResponse.of(
                            "청년",
                            "B타입 일반공급",
                            74.45,
                            50,
                            15000000.0,
                            150000.0,
                            20000000.0,
                            200000.0
                        ),
                        RoomRentalConditionResponse.of(
                            "청년,신혼부부",
                            "A타입 특별공급",
                            54.23,
                            25,
                            10000000.0,
                            100000.0,
                            15000000.0,
                            150000.0
                        ),
                        RoomRentalConditionResponse.of(
                            "청년,신혼부부",
                            "B타입 특별공급",
                            74.45,
                            25,
                            15000000.0,
                            150000.0,
                            200000000.0,
                            2000000.0
                        )
                    ),
                    "https://www.google.com"
                )
            )
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
