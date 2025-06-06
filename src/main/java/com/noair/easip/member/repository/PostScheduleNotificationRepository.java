package com.noair.easip.member.repository;

import com.noair.easip.member.domain.PostScheduleNotification;
import com.noair.easip.member.domain.PostScheduleNotificationId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostScheduleNotificationRepository extends JpaRepository<PostScheduleNotification, PostScheduleNotificationId> {
    boolean existsByPostScheduleIdAndMemberId(String postScheduleId, String memberId);

    boolean existsByPostScheduleIdInAndMemberId(List<String> postScheduleIds, String memberId);

    void deleteByPostScheduleIdAndMemberId(String postScheduleId, String memberId);

    List<PostScheduleNotification> findAllByMemberId(String loginMemberId);
}
