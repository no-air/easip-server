package com.noair.easip.house.service;

import com.noair.easip.house.controller.dto.HouseDetailResponse;
import com.noair.easip.house.controller.dto.HouseElementResponse;
import com.noair.easip.house.domain.House;
import com.noair.easip.house.exception.HouseNotFoundException;
import com.noair.easip.house.repository.HouseRepository;
import com.noair.easip.post.service.PostHouseService;
import com.noair.easip.post.service.PostScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.noair.easip.house.domain.RentalType.*;

@Service
@RequiredArgsConstructor
public class HouseService {
    private final HouseRepository houseRepository;
    private final HouseImageService houseImageService;
    private final PostHouseService postHouseService;
    private final PostScheduleService postScheduleService;
    private final RoomRentalConditionService roomRentalConditionService;

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

    public HouseDetailResponse getHouseDetail(String houseId) {
        House house = getHouseById(houseId);
        return HouseDetailResponse.of(
                house.getId(),
                house.getName(),
                house.getBadgeNames(),
                houseImageService.getThumbnailImageUrls(house.getId()),
                house.getAddress(),
                house.getNearStation(),
                house.getDeveloperName(),
                house.getConstructorName(),
                house.getFirstRecruitmentDate(),
                house.getMoveInDate(),
                house.getGeneralSupplyCount(),
                roomRentalConditionService.getRoomInfoResponses(houseId, GENERAL),
                house.getSpecialSupplyCount(),
                roomRentalConditionService.getRoomInfoResponses(houseId, SPECIAL),
                houseImageService.getFloorPlanImageUrls(house.getId()),
                house.getLatitude(),
                house.getLongitude()
        );
    }

    public List<HouseElementResponse> makeHouseElementResponses(List<House> houses) {
        return houses.stream()
                .map(house -> HouseElementResponse.of(
                        house.getId(),
                        houseImageService.getThumbnailImageUrl(house.getId()),
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
        return houseImageService.getThumbnailImageUrl(thumbnailHouseId);
    }

}
