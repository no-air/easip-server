package com.noair.easip.post.repository;

import com.noair.easip.post.domain.PostSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface PostScheduleRepository extends JpaRepository<PostSchedule, String> {
    boolean existsByPost_IdAndStartDateTimeLessThan(String postId, LocalDateTime dateTime);

    boolean existsByPost_IdAndStartDateTimeGreaterThan(String postId, LocalDateTime dateTime);
}
