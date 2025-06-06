package com.noair.easip.post.repository;

import com.noair.easip.post.domain.PostSchedule;
import com.noair.easip.post.domain.ScheduleType;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PostScheduleRepositoryCustom {
    Optional<PostSchedule> findRecentByHouseIdAndScheduleType(String houseId, ScheduleType scheduleType);

    boolean existsByHouseIdAndDateTimeGreaterThan(String houseId, LocalDateTime dateTime);

    boolean existsByPostIdAndDateTimeGreaterThan(String postId, LocalDateTime dateTime);
}
