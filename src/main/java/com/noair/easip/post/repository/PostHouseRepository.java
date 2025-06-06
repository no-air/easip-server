package com.noair.easip.post.repository;

import com.noair.easip.post.domain.PostHouse;
import com.noair.easip.post.domain.PostHouseId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostHouseRepository extends JpaRepository<PostHouse, PostHouseId>, PostHouseRepositoryCustom {
    List<PostHouse> findAllByPostIdAndHouseId(String postId, String houseId);
}
