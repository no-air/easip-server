package com.noair.easip.post.service;

import com.noair.easip.member.repository.PostScheduleNotificationRepository;
import com.noair.easip.post.controller.dto.ScheduleDto;
import com.noair.easip.post.domain.Post;
import com.noair.easip.post.domain.PostSchedule;
import com.noair.easip.post.domain.SUBSCRIPTION_STATE;
import com.noair.easip.post.exception.PostScheduleNotFoundException;
import com.noair.easip.post.repository.PostScheduleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static com.noair.easip.post.domain.ScheduleType.APPLICATION;

@Service
@RequiredArgsConstructor
public class PostScheduleService {
    private final PostScheduleRepository postScheduleRepository;
    private final PostScheduleNotificationRepository postScheduleNotificationRepository;

    public PostSchedule create(PostSchedule postSchedule) {
        return postScheduleRepository.save(postSchedule);
    }

    public PostSchedule getPostScheduleById(String postScheduleId) {
        return postScheduleRepository.findById(postScheduleId)
                .orElseThrow(PostScheduleNotFoundException::new);
    }

    public String getSubscriptionStateKorNameByPostId(String postId) {
        Optional<PostSchedule> applicationStart = postScheduleRepository.findByPostIdAndScheduleType(postId, APPLICATION);

        // 청약신청 타입의 일정이 없거나, 시작일시가 null이거나, 현재가 신청일정보다 이전이라면 = "청약예정"
        if (
                applicationStart.isEmpty() || applicationStart.get().getStartDateTime() == null ||
                        LocalDateTime.now().isBefore(applicationStart.get().getStartDateTime())
        ) {
            return SUBSCRIPTION_STATE.SCHEDULED.getKorName();

            // 현재가 모든 일정보다 이후라면 = "청약완료"
        } else if (!postScheduleRepository.existsByPostIdAndDateTimeGreaterThan(postId, LocalDateTime.now())) {
            return SUBSCRIPTION_STATE.COMPLETED.getKorName();

            // 그 외에는 "청약진행중"
        } else {
            return SUBSCRIPTION_STATE.ONGOING.getKorName();
        }
    }

    public String getSubscriptionStateKotNameByHouseId(String houseId) {
        Optional<PostSchedule> applicationStart = postScheduleRepository.findRecentByHouseIdAndScheduleType(houseId, APPLICATION);

        // 청약신청 타입의 일정이 없거나, 시작일시가 null이거나, 현재가 신청일정보다 이전이라면 = "청약예정"
        if (
                applicationStart.isEmpty() || applicationStart.get().getStartDateTime() == null ||
                        LocalDateTime.now().isBefore(applicationStart.get().getStartDateTime())
        ) {
            return SUBSCRIPTION_STATE.SCHEDULED.getKorName();

            // 현재가 모든 일정보다 이후라면 = "청약완료"
        } else if (!postScheduleRepository.existsByHouseIdAndDateTimeGreaterThan(houseId, LocalDateTime.now())) {
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

    public List<ScheduleDto> getScheduleDtoByPost(Post post, String loginMemberId) {
        return post.getSchedules().stream()
                .sorted(Comparator.comparing(PostSchedule::getOrdering))
                .map(schedule -> ScheduleDto.of(
                        schedule.getId(),
                        schedule.getTitle(),
                        schedule.getStartDateTime() != null ? schedule.getStartDateTime().toString() : schedule.getStartNote(),
                        schedule.getEndDateTime() != null ? schedule.getEndDateTime().toString() : schedule.getEndNote(),
                        postScheduleNotificationRepository.existsByPostScheduleIdAndMemberId(schedule.getId(), loginMemberId)
                ))
                .toList();
    }

    public boolean isPushAlarmRegistered(String postId, String loginMemberId) {
        List<String> scheduleIds = postScheduleRepository.findAllByPostId(postId).stream().map(PostSchedule::getId).toList();
        return postScheduleNotificationRepository.existsByPostScheduleIdInAndMemberId(scheduleIds, loginMemberId);
    }

    public int getPushAlarmRegisteredPostCount(String loginMemberId) {
        return (int) postScheduleNotificationRepository.findAllByMemberId(loginMemberId).stream()
                .flatMap(notification -> Stream.of(notification.getPostSchedule().getPost()))
                .distinct()
                .count();
    }

    @Transactional
    public void deleteAllByMemberId(String memberId) {
        postScheduleNotificationRepository.deleteAllByMemberId(memberId);
    }
}
