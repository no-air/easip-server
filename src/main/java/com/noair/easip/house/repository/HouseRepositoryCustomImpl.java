package com.noair.easip.house.repository;

import com.noair.easip.house.domain.House;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.noair.easip.house.domain.QHouse.house;

@RequiredArgsConstructor
public class HouseRepositoryCustomImpl implements HouseRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<House> searchByLocation(Double minLatitude, Double minLongitude, Double maxLatitude, Double maxLongitude) {
        return queryFactory
                .selectFrom(house)
                .where(
                        house.latitude.between(minLatitude, maxLatitude),
                        house.longitude.between(minLongitude, maxLongitude)
                )
                .fetch();
    }

    @Override
    public House findByCompactAddress(String compactAddress) {
        return queryFactory
                .selectFrom(house)
                .where(
                        Expressions.stringTemplate(
                                "replace({0}, ' ', '')", house.roadAddress
                        ).contains(compactAddress).or(
                                Expressions.stringTemplate(
                                        "replace({0}, ' ', '')", house.jibunAddress
                                ).contains(compactAddress)
                        )
                )
                .fetchOne();
    }

    @Override
    public House findByCompactHouseName(String compactHouseName) {
        return queryFactory
                .selectFrom(house)
                .where(
                        Expressions.stringTemplate(
                                "replace({0}, ' ', '')", house.name
                        ).contains(compactHouseName)
                )
                .fetchOne();
    }

    @Override
    public House findByCompactPageUrl(String compactPageUrl) {
        return queryFactory
                .selectFrom(house)
                .where(
                        Expressions.stringTemplate(
                                "replace({0}, ' ', '')", house.pageUrl
                        ).contains(compactPageUrl)
                )
                .fetchOne();
    }
}
