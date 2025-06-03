package com.noair.easip.member.service;

import com.noair.easip.member.repository.PostScheduleNotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostScheduleNotificationService {
    private final PostScheduleNotificationRepository postScheduleNotificationRepository;

    public boolean existsByPostScheduleIdAndMemberId(String postScheduleId, String memberId) {
        return postScheduleNotificationRepository.existsByPostSchedule_IdAndMember_Id(postScheduleId, memberId);
    }
}
