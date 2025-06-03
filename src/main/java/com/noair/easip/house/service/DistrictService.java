package com.noair.easip.house.service;

import com.noair.easip.house.controller.dto.DistrictDto;
import com.noair.easip.house.domain.District;
import com.noair.easip.house.exception.DistrictNotFoundException;
import com.noair.easip.house.repository.DistrictRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DistrictService {
    private final DistrictRepository districtRepository;

    public District getDistrictById(String districtId) {
        return districtRepository.findById(districtId)
                .orElseThrow(DistrictNotFoundException::new);
    }

    public List<DistrictDto> getAllDistricts() {
        return districtRepository.findAll().stream()
                .map(DistrictDto::of)
                .toList();
    }
}
