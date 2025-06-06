package com.noair.easip.post.repository;

import com.noair.easip.house.controller.dto.RentDto;
import com.noair.easip.member.domain.Position;
import com.noair.easip.post.controller.dto.PostHouseConditionDto;

import java.util.List;

public interface PostHouseRepositoryCustom {
    List<String> findDistinctHouseIdByPostId(String postId);

    List<Position> findDistinctSupplyTypeByPostIdAndHouseId(String postId, String houseId);

    List<RentDto> findRentDtosByPostIdAndHouseId(String postId, String houseId);

    int sumSupplyRoomCountByPostIdAndHouseId(String postId, String houseId);
}
