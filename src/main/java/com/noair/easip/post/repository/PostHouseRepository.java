package com.noair.easip.post.repository;

import com.noair.easip.post.domain.PostHouse;
import com.noair.easip.post.domain.PostHouseId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostHouseRepository extends JpaRepository<PostHouse, PostHouseId>, PostHouseRepositoryCustom {
}
