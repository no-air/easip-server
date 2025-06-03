package com.noair.easip.house.service;

import com.noair.easip.house.domain.House;
import com.noair.easip.house.exception.HouseNotFoundException;
import com.noair.easip.house.repository.HouseImageRepository;
import com.noair.easip.house.repository.HouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HouseService {
    private final HouseRepository houseRepository;

    public House getHouseById(String houseId) {
        return houseRepository.findById(houseId)
                .orElseThrow(HouseNotFoundException::new);
    }
}
