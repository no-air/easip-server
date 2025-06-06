package com.noair.easip.member.repository;

import com.noair.easip.member.domain.PostScheduleNotification;
import com.noair.easip.member.domain.PostScheduleNotificationId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostScheduleNotificationRepository extends JpaRepository<PostScheduleNotification, PostScheduleNotificationId> {
    boolean existsByPostSchedule_IdAndMember_Id(String postScheduleId, String memberId);

    boolean existsByPostSchedule_IdInAndMember_Id(List<String> postScheduleIds, String memberId);

    void deleteByPostSchedule_IdAndMember_Id(String postScheduleId, String memberId);

    List<PostScheduleNotification> findAllByPostSchedule_IdInAndMember_Id(List<String> scheduleIds, String loginMemberId);

    List<PostScheduleNotification> findAllByMember_Id(String loginMemberId);
}
