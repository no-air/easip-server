package com.noair.easip.house.service;

import com.noair.easip.house.domain.House;
import com.noair.easip.house.exception.HouseNotFoundException;
import com.noair.easip.house.repository.HouseRepository;
import com.noair.easip.post.service.PostHouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HouseService {
    private final HouseRepository houseRepository;
    private final PostHouseService postHouseService;

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
}
