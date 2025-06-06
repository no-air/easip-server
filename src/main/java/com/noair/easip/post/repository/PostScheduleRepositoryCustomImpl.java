package com.noair.easip.post.repository;

import com.noair.easip.post.domain.PostSchedule;
import com.noair.easip.post.domain.ScheduleType;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.noair.easip.post.domain.QPostHouse.postHouse;
import static com.noair.easip.post.domain.QPostSchedule.postSchedule;

@RequiredArgsConstructor
public class PostScheduleRepositoryCustomImpl implements PostScheduleRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<PostSchedule> findRecentByHouseIdAndScheduleType(String houseId, ScheduleType scheduleType) {
        PostSchedule result = jpaQueryFactory
                .selectFrom(postSchedule)
                .innerJoin(postHouse).on(postSchedule.post.id.eq(postHouse.post.id))
                .where(
                        postHouse.house.id.eq(houseId),
                        postSchedule.scheduleType.eq(scheduleType)
                )
                .orderBy(postSchedule.startDate.desc())
                .fetchFirst();

        return Optional.ofNullable(result);
    }

    @Override
    public boolean existsByPostIdAndDateTimeGreaterThan(String postId, LocalDateTime dateTime) {
        return jpaQueryFactory
                .selectFrom(postSchedule)
                .where(
                        postSchedule.post.id.eq(postId).and(
                                postSchedule.startDateTime.gt(dateTime).or(postSchedule.endDateTime.gt(dateTime))
                        )
                )
                .fetchFirst() != null;
    }

    @Override
    public boolean existsByHouseIdAndDateTimeGreaterThan(String houseId, LocalDateTime dateTime) {
        return jpaQueryFactory
                .selectFrom(postSchedule)
                .innerJoin(postHouse).on(postSchedule.post.id.eq(postHouse.post.id))
                .where(
                        postHouse.house.id.eq(houseId).and(
                                postSchedule.startDateTime.gt(dateTime).or(postSchedule.endDateTime.gt(dateTime))
                        )
                )
                .orderBy(postSchedule.startDate.desc())
                .fetchFirst() != null;
    }
}
