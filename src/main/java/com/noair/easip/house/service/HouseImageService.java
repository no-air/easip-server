package com.noair.easip.house.service;

import com.noair.easip.house.domain.HouseImage;
import com.noair.easip.house.domain.HouseImageType;
import com.noair.easip.house.repository.HouseImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HouseImageService {
    private final HouseImageRepository houseImageRepository;

    public String getThumbnailUrl(String houseId) {
        return houseImageRepository
                .findFirstByHouseIdAndHouseImageTypeOrderByOrderingAsc(houseId, HouseImageType.THUMBNAIL)
                .map(HouseImage::getUrl)
                .orElse("");
    }
}
