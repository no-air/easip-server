package com.noair.easip.house.service;

import com.noair.easip.house.controller.dto.HouseElementResponse;
import com.noair.easip.house.domain.House;
import com.noair.easip.house.exception.HouseNotFoundException;
import com.noair.easip.house.repository.HouseRepository;
import com.noair.easip.post.service.PostHouseService;
import com.noair.easip.post.service.PostScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HouseService {
    private final HouseRepository houseRepository;
    private final HouseImageService houseImageService;
    private final PostHouseService postHouseService;
    private final PostScheduleService postScheduleService;

    public House getHouseById(String houseId) {
        return houseRepository.findById(houseId)
                .orElseThrow(HouseNotFoundException::new);
    }

    public List<House> getHousesByPostId(String postId) {
        return postHouseService.getHouseIdsByPostId(postId)
                .stream()
                .map(this::getHouseById)
                .toList();
    }

    public List<House> getHousesByLocation(Double minLatitude, Double minLongitude, Double maxLatitude, Double maxLongitude) {
        return houseRepository.searchByLocation(minLatitude, minLongitude, maxLatitude, maxLongitude);
    }

    public List<HouseElementResponse> searchHouseInMap(Double minLatitude, Double minLongitude, Double maxLatitude, Double maxLongitude) {
        List<House> houses = getHousesByLocation(minLatitude, minLongitude, maxLatitude, maxLongitude);
        return makeHouseElementResponses(houses);
    }

    public List<HouseElementResponse> makeHouseElementResponses(List<House> houses) {
        return houses.stream()
                .map(house -> HouseElementResponse.of(
                        house.getId(),
                        houseImageService.getThumbnailUrl(house.getId()),
                        house.getName(),
                        postScheduleService.getSubscriptionStateKotNameByHouseId(house.getId()),
                        house.getDistrict().getName(),
                        house.getLatitude(),
                        house.getLongitude()
                ))
                .toList();
    }

    public String getHouseThumbnailUrlByPostId(String postId) {
        String thumbnailHouseId = getHousesByPostId(postId).getFirst().getId();
        return houseImageService.getThumbnailUrl(thumbnailHouseId);
    }

}
