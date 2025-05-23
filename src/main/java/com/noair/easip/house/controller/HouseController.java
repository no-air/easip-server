package com.noair.easip.house.controller;

import com.noair.easip.house.controller.dto.HouseDetailResponse;
import com.noair.easip.house.controller.dto.HouseSummaryResponse;
import com.noair.easip.house.controller.dto.RoomInfoResponse;
import com.noair.easip.util.ArrayResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;

@Tag(name = "주택 API")
@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/v1/houses")
public class HouseController {

    @Operation(summary = "[MOCK] 지도 기반 주택 목록 조회")
    @GetMapping("/map")
    ArrayResponse<HouseSummaryResponse> getHouseListInMap(
        @Parameter(description = "취소 위도", example = "37.5665")
        @RequestParam
        Double minLatitude,

        @Parameter(description = "취소 경도", example = "126.978")
        @RequestParam
        Double minLongitude,

        @Parameter(description = "최대 위도", example = "37.5665")
        @RequestParam
        Double maxLatitude,

        @Parameter(description = "최대 경도", example = "126.978")
        @RequestParam
        Double maxLongitude
    ) {
        return ArrayResponse.of(
            List.of(
                HouseSummaryResponse.of(
                    "01HGW2N7EHJVJ4CJ999RRS2E97",
                    "https://travel.rakuten.co.jp/mytrip/sites/mytrip/files/migration_article_images/ranking/spot-fukuoka-fukuoka_02.jpg",
                    "KRX한국거래소",
                    "청약예정",
                    "영등포구",
                    37.522875991257024,
                    126.92803891060132
                ),
                HouseSummaryResponse.of(
                    "01HGW2N7EHJVJ4CJ999RRS2E91",
                    "https://travel.rakuten.co.jp/mytrip/sites/mytrip/files/migration_article_images/ranking/spot-fukuoka-fukuoka_02.jpg",
                    "코스콤 신사옥",
                    "청약예정",
                    "영등포구",
                    37.52166435840751,
                    126.92839358143895
                ),
                HouseSummaryResponse.of(
                    "01HGW2N7EHJVJ4CJ999RRS2E92",
                    "https://travel.rakuten.co.jp/mytrip/sites/mytrip/files/migration_article_images/ranking/spot-fukuoka-fukuoka_02.jpg",
                    "더현대 서울",
                    "청약예정",
                    "영등포구",
                    37.52588558605109,
                    126.92844611120303
                )
            )
        );
    }

    @Operation(summary = "[MOCK] 주택 상세 조회")
    @GetMapping("/{houseId}")
    HouseDetailResponse getHouseDetail(
        @Parameter(description = "주택 ID", example = "01HGW2N7EHJVJ4CJ999RRS2E97")
        @PathVariable("houseId") 
        String houseId
    ) {
        return HouseDetailResponse.of(
            "01HGW2N7EHJVJ4CJ999RRS2E97",
            "신당역 신당큐브스테이트오피스텔",
            List.of("민간임대", "청약예정", "중구"),
            List.of(
                "https://travel.rakuten.co.jp/mytrip/sites/mytrip/files/migration_article_images/ranking/spot-fukuoka-fukuoka_02.jpg",
                "https://travel.rakuten.co.jp/mytrip/sites/mytrip/files/migration_article_images/ranking/spot-fukuoka-fukuoka_02.jpg"
            ),
            "서울특별시 중구 신당동 123-45",
            "신당역 2호선, 3호선",
            "더클래식동작",
            "(주)강남건영",
            "2025-01-01",
            "2025-02-01",
            100,
            List.of(
                RoomInfoResponse.of(
                    "원룸",
                    52.23,
                    "청년",
                    50,
                    100000000,
                    1000000,
                    150000
                ),
                RoomInfoResponse.of(
                    "투룸",
                    72.45,
                    "신혼부부",
                    50,
                    150000000,
                    1500000,
                    200000
                )
            ),
            50,
            List.of(
                RoomInfoResponse.of(
                    "원룸",
                    52.23,
                    "다자녀가구",
                    25,
                    100000000,
                    1000000,
                    150000
                ),
                RoomInfoResponse.of(
                    "투룸",
                    72.45,
                    "다자녀가구",
                    25,
                    150000000,
                    1500000,
                    200000
                )
            ),
            List.of(
                "https://travel.rakuten.co.jp/mytrip/sites/mytrip/files/migration_article_images/ranking/spot-fukuoka-fukuoka_02.jpg",
                "https://travel.rakuten.co.jp/mytrip/sites/mytrip/files/migration_article_images/ranking/spot-fukuoka-fukuoka_02.jpg"
            ),
            37.5665,
            126.978
        );
    }

    @Operation(summary = "[MOCK: 랜덤값] 북마크 여부 조회")
    @GetMapping("/{houseId}/bookmark")
    Boolean getHouseBookmark(
        @Parameter(description = "주택 ID", example = "01HGW2N7EHJVJ4CJ999RRS2E97")
        @PathVariable("houseId") 
        String houseId
    ) {
        return new Random().nextBoolean();
    }
}
