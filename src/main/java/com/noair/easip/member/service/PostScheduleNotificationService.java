package com.noair.easip.member.service;

import com.noair.easip.member.domain.Member;
import com.noair.easip.member.domain.PostScheduleNotification;
import com.noair.easip.member.domain.PostScheduleNotificationId;
import com.noair.easip.member.repository.PostScheduleNotificationRepository;
import com.noair.easip.post.domain.PostSchedule;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostScheduleNotificationService {
    private final PostScheduleNotificationRepository postScheduleNotificationRepository;

    @Transactional
    public void toggleScheduleNotification(PostSchedule postSchedule, Member loginMember) {
        boolean isSubscribed = postScheduleNotificationRepository.existsByPostScheduleIdAndMemberId(postSchedule.getId(), loginMember.getId());

        if (isSubscribed) {
            postScheduleNotificationRepository.deleteByPostScheduleIdAndMemberId(postSchedule.getId(), loginMember.getId());
        } else {
            postScheduleNotificationRepository.save(
                    PostScheduleNotification.builder()
                            .id(new PostScheduleNotificationId(postSchedule.getId(), loginMember.getId()))
                            .postSchedule(postSchedule)
                            .member(loginMember)
                            .build()
            );
        }
    }
}
