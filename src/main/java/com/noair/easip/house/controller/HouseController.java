package com.noair.easip.house.controller;

import com.noair.easip.house.controller.dto.*;
import com.noair.easip.house.service.HouseService;
import com.noair.easip.post.controller.dto.ApplicationConditionDto;
import com.noair.easip.util.ArrayResponse;
import com.noair.easip.util.DefaultResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "주택 API")
@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/v1/houses")
public class HouseController {
    private final HouseService houseService;

    @Operation(summary = "지도 기반 주택 목록 조회")
    @GetMapping("/map")
    ArrayResponse<HouseElementResponse> getHouseListInMap(
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
                houseService.searchHouseInMap(minLatitude, minLongitude, maxLatitude, maxLongitude)
        );
    }

    @Operation(summary = "주택 상세 조회")
    @GetMapping("/{houseId}")
    HouseDetailResponse getHouseDetail(
        @Parameter(description = "주택 ID", example = "01HGW2N7EHJVJ4CJ999RRS2E97")
        @PathVariable("houseId") 
        String houseId
    ) {
        return houseService.getHouseDetail(houseId);
    }

    @Operation(summary = "[MOCK: 랜덤값] 북마크 여부 조회")
    @GetMapping("/{houseId}/bookmark")
    Boolean getHouseBookmark(
        @Parameter(description = "주택 ID", example = "01HGW2N7EHJVJ4CJ999RRS2E97")
        @PathVariable
        String houseId
    ) {
        return new Random().nextBoolean();
    }

    @Operation(summary = "[MOCK: 랜덤값] 북마크 토글")
    @PutMapping("/{houseId}/bookmark")
    DefaultResponse toggleHouseBookmark(
        @Parameter(description = "주택 ID", example = "01HGW2N7EHJVJ4CJ999RRS2E97")
        @PathVariable
        String houseId
    ) {
        return new Random().nextBoolean() ? DefaultResponse.ok() : DefaultResponse.fail();
    }

}
