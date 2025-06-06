package com.noair.easip.post.service;

import com.noair.easip.house.controller.dto.RentDto;
import com.noair.easip.member.domain.Position;
import com.noair.easip.post.controller.dto.PostHouseConditionDto;
import com.noair.easip.post.domain.PostHouse;
import com.noair.easip.post.repository.PostHouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostHouseService {
    private final PostHouseRepository postHouseRepository;

    public List<String> getHouseIdsByPostId(String postId) {
        return postHouseRepository.findDistinctHouseIdByPostId(postId);
    }

    public List<Position> getSupplyTypesByPostIdAndHouseHid(String postId, String houseId) {
        return postHouseRepository.findDistinctSupplyTypeByPostIdAndHouseId(postId, houseId);
    }

    public List<RentDto> getRentDtosByPostIdAndHouseId(String postId, String houseId) {
        return postHouseRepository.findRentDtosByPostIdAndHouseId(postId, houseId);
    }

    public int getNumberOfUnitsRecruitingByPostIdAndHouseId(String postId, String houseId) {
        return postHouseRepository.sumSupplyRoomCountByPostIdAndHouseId(postId, houseId);
    }

    public List<PostHouseConditionDto> getPostHouseConditionDtos(String postId, String houseId) {
        List<PostHouse> postHouse = postHouseRepository.findAllByPostIdAndHouseId(postId, houseId);

        return postHouse.stream()
                .collect(Collectors.groupingBy(h ->
                        h.getId().getSupplyType() + "|" + h.getId().getLivingType()
                ))
                .values().stream()
                .map(group -> {
                    PostHouse first = group.getFirst();

                    String supplyTypeKor = first.getId().getSupplyType().getKorName();
                    String livingType = first.getId().getLivingType();
                    Integer supplyRoomCount = first.getSupplyRoomCount();

                    Double minRatioDeposit = group.stream()
                            .map(h -> h.getId().getDeposit())
                            .min(Double::compareTo).orElse(0.0);
                    Double minRatioMonthlyRent = group.stream()
                            .map(h -> h.getId().getMonthlyRent())
                            .max(Double::compareTo).orElse(0.0);
                    Double maxRatioDeposit = group.stream()
                            .map(h -> h.getId().getDeposit())
                            .max(Double::compareTo).orElse(0.0);
                    Double maxRatioMonthlyRent = group.stream()
                            .map(h -> h.getId().getMonthlyRent())
                            .min(Double::compareTo).orElse(0.0);

                    return PostHouseConditionDto.of(
                            supplyTypeKor,
                            livingType,
                            supplyRoomCount,
                            minRatioDeposit,
                            minRatioMonthlyRent,
                            maxRatioDeposit,
                            maxRatioMonthlyRent
                    );
                })
                .sorted(
                        Comparator.comparing(PostHouseConditionDto::supplyType).reversed() // 내림차순: .reversed()
                                .thenComparing(PostHouseConditionDto::livingType) // 오름차순: 기본 정렬
                                .thenComparing(PostHouseConditionDto::minRatioDeposit)
                )
                .toList();
    }
}
