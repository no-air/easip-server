package com.noair.easip.post.service;

import com.noair.easip.house.controller.dto.RentDto;
import com.noair.easip.member.domain.Position;
import com.noair.easip.post.repository.PostHouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
