package com.noair.easip.post.service;

import com.noair.easip.post.domain.PostSchedule;
import com.noair.easip.post.domain.SUBSCRIPTION_STATE;
import com.noair.easip.post.repository.PostScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.noair.easip.post.domain.ScheduleType.*;

@Service
@RequiredArgsConstructor
public class PostScheduleService {
    private final PostScheduleRepository postScheduleRepository;

    public String getSubscriptionStateKorName(String postId) {
        Optional<PostSchedule> applicationStart = postScheduleRepository.findByPostIdAndScheduleType(postId, APPLICATION);

        // 청약신청 타입의 일정이 있고, 현재가 일정보다 이전이라면 = "청약예정"
        if (applicationStart.isPresent() &&
                applicationStart.get().getStartDateTime() != null && LocalDateTime.now().isBefore(applicationStart.get().getStartDateTime())
        ) {
            return SUBSCRIPTION_STATE.SCHEDULED.getKorName();

        // 청약신청 타입의 일정이 없거나, 시작일시가 null이거나, 현재가 모든 일정보다 이후라면 = "청약완료"
        } else if (applicationStart.isEmpty() || applicationStart.get().getStartDateTime() == null || !postScheduleRepository.existsByPost_IdAndStartDateTimeGreaterThan(postId, LocalDateTime.now())) {
            return SUBSCRIPTION_STATE.COMPLETED.getKorName();

        // 그 외에는 "청약진행중"
        } else {
            return SUBSCRIPTION_STATE.ONGOING.getKorName();
        }
    }

    public String getApplicationStartStringByPostId(String postId) {
        return postScheduleRepository.findByPostIdAndScheduleType(postId, APPLICATION)
                .map(schedule -> {
                    if (schedule.getStartDateTime() != null) {
                        return schedule.getStartDateTime().toString();
                    } else {
                        return schedule.getStartNote();
                    }
                })
                .orElse("");
    }

    public String getApplicationEndStringByPostId(String postId) {
        return postScheduleRepository.findByPostIdAndScheduleType(postId, APPLICATION)
                .map(schedule -> {
                    if (schedule.getEndDateTime() != null) {
                        return schedule.getEndDateTime().toString();
                    } else {
                        return schedule.getEndNote();
                    }
                })
                .orElse("");
    }
}
