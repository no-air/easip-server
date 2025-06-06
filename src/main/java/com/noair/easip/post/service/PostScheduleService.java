package com.noair.easip.post.service;

import com.noair.easip.member.domain.Member;
import com.noair.easip.member.domain.PostScheduleNotification;
import com.noair.easip.member.domain.PostScheduleNotificationId;
import com.noair.easip.member.repository.PostScheduleNotificationRepository;
import com.noair.easip.member.service.MemberService;
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

import static com.noair.easip.post.domain.ScheduleType.APPLICATION;

@Service
@RequiredArgsConstructor
public class PostScheduleService {
    private final PostScheduleRepository postScheduleRepository;
    private final PostScheduleNotificationRepository postScheduleNotificationRepository;
    private final MemberService memberService;

    @Transactional
    public void toggleScheduleNotification(String postScheduleId, String loginMemberId) {
        boolean isSubscribed = postScheduleNotificationRepository.existsByPostSchedule_IdAndMember_Id(postScheduleId, loginMemberId);

        if (isSubscribed) {
            postScheduleNotificationRepository.deleteByPostSchedule_IdAndMember_Id(postScheduleId, loginMemberId);
        } else {
            PostSchedule postSchedule = getPostScheduleById(postScheduleId);
            Member member = memberService.getMemberById(loginMemberId);
            postScheduleNotificationRepository.save(
                    PostScheduleNotification.builder()
                            .id(new PostScheduleNotificationId(postSchedule.getId(), member.getId()))
                            .postSchedule(postSchedule)
                            .member(member)
                            .build()
            );
        }
    }

    public PostSchedule getPostScheduleById(String postScheduleId) {
        return postScheduleRepository.findById(postScheduleId)
                .orElseThrow(PostScheduleNotFoundException::new);
    }

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

    public List<ScheduleDto> getScheduleDtoByPost(Post post, String loginMemberId) {
        return post.getSchedules().stream()
                .sorted(Comparator.comparing(PostSchedule::getOrdering))
                .map(schedule -> ScheduleDto.of(
                        schedule.getId(),
                        schedule.getTitle(),
                        schedule.getStartDateTime() != null ? schedule.getStartDateTime().toString() : schedule.getStartNote(),
                        schedule.getEndDateTime() != null ? schedule.getEndDateTime().toString() : schedule.getEndNote(),
                        postScheduleNotificationRepository.existsByPostSchedule_IdAndMember_Id(schedule.getId(), loginMemberId)
                ))
                .toList();
    }
}
