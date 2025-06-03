package com.noair.easip.house.controller;

import com.noair.easip.house.controller.dto.DistrictDto;
import com.noair.easip.house.service.DistrictService;
import com.noair.easip.util.ArrayResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "자치구 API")
@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/v1/districts")
public class DistrictController {
    private final DistrictService districtService;

    @Operation(summary = "자치구 목록 조회")
    @GetMapping
    ArrayResponse<DistrictDto> getDistricts() {
        return ArrayResponse.of(districtService.getAllDistricts());
    }
}
