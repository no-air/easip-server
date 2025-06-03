package com.noair.easip.post.repository;

import com.noair.easip.house.controller.dto.RentDto;
import com.noair.easip.member.domain.Position;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.noair.easip.post.domain.QPostHouse.postHouse;

@RequiredArgsConstructor
public class PostHouseRepositoryCustomImpl implements PostHouseRepositoryCustom {
    private final JPAQueryFactory queryFactory;


    @Override
    public List<String> findDistinctHouseIdByPostId(String postId) {
        return queryFactory
                .selectDistinct(postHouse.id.houseId)
                .from(postHouse)
                .where(postHouse.post.id.eq(postId))
                .fetch();
    }

    @Override
    public List<Position> findDistinctSupplyTypeByPostIdAndHouseId(String postId, String houseId) {
        return queryFactory
                .selectDistinct(postHouse.id.supplyType)
                .from(postHouse)
                .where(postHouse.post.id.eq(postId), postHouse.id.houseId.eq(houseId))
                .fetch();
    }

    @Override
    public List<RentDto> findRentDtosByPostIdAndHouseId(String postId, String houseId) {
        return queryFactory
                .selectDistinct(
                        Projections.constructor(
                                RentDto.class,
                                postHouse.id.deposit,
                                postHouse.id.monthlyRent
                        )
                )
                .from(postHouse)
                .where(postHouse.post.id.eq(postId), postHouse.id.houseId.eq(houseId))
                .fetch();
    }

    @Override
    public int sumSupplyRoomCountByPostIdAndHouseId(String postId, String houseId) {
        Integer sum = queryFactory
                .select(postHouse.supplyRoomCount.sum())
                .from(postHouse)
                .where(postHouse.post.id.eq(postId), eqHouseId(houseId))
                .fetchOne();

        return sum != null ? sum : 0;
    }

    private BooleanExpression eqHouseId(String houseId) {
        return houseId != null ? postHouse.id.houseId.eq(houseId) : null;
    }
}
