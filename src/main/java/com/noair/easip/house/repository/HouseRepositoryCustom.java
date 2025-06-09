package com.noair.easip.house.repository;

import com.noair.easip.house.domain.House;

import java.util.List;

public interface HouseRepositoryCustom {
    List<House> searchByLocation(Double minLatitude, Double minLongitude, Double maxLatitude, Double maxLongitude);

    House findByCompactAddress(String compactAddress);

    House findByCompactHouseName(String compactHouseName);

    House findByCompactPageUrl(String compactPageUrl);
}
