package com.noair.easip.house.service;

import com.noair.easip.house.controller.dto.DistrictDto;
import com.noair.easip.house.repository.DistrictRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DistrictService {
    private final DistrictRepository districtRepository;

    public List<DistrictDto> getAllDistricts() {
        return districtRepository.findAll().stream()
                .map(DistrictDto::of)
                .toList();
    }
}
