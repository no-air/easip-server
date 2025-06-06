package com.noair.easip.post.repository;

import com.noair.easip.post.domain.PostSchedule;
import com.noair.easip.post.domain.ScheduleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PostScheduleRepository extends JpaRepository<PostSchedule, String>, PostScheduleRepositoryCustom {
    Optional<PostSchedule> findByPostIdAndScheduleType(String postId, ScheduleType scheduleType);

    List<PostSchedule> findAllByPostId(String postId);
}
