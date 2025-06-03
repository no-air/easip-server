package com.noair.easip.house.repository;

import com.noair.easip.house.domain.House;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HouseRepository extends JpaRepository<House, String>, HouseRepositoryCustom {
}
