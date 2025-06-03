package com.noair.easip.house.repository;

import com.noair.easip.house.domain.HouseImage;
import com.noair.easip.house.domain.HouseImageType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HouseImageRepository extends JpaRepository<HouseImage, String> {
    Optional<HouseImage> findFirstByHouseIdAndHouseImageTypeOrderByOrderingAsc(
            String houseId,
            HouseImageType houseImageType
    );
}
