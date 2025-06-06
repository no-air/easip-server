package com.noair.easip.house.service;

import com.noair.easip.house.domain.HouseImage;
import com.noair.easip.house.domain.HouseImageType;
import com.noair.easip.house.repository.HouseImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HouseImageService {
    private final HouseImageRepository houseImageRepository;

    public String getThumbnailImageUrl(String houseId) {
        return houseImageRepository
                .findFirstByHouseIdAndHouseImageTypeOrderByOrderingAsc(houseId, HouseImageType.THUMBNAIL)
                .map(HouseImage::getUrl)
                .orElse("");
    }

    public List<String> getThumbnailImageUrls(String houseId) {
        return houseImageRepository
                .findAllByHouseIdAndHouseImageTypeOrderByOrderingAsc(houseId, HouseImageType.THUMBNAIL)
                .stream()
                .map(HouseImage::getUrl)
                .toList();
    }

    public List<String> getFloorPlanImageUrls(String houseId) {
        return houseImageRepository
                .findAllByHouseIdAndHouseImageTypeOrderByOrderingAsc(houseId, HouseImageType.FLOOR_PLAN)
                .stream()
                .map(HouseImage::getUrl)
                .toList();
    }
}
