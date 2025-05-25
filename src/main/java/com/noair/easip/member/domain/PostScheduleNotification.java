package com.noair.easip.member.domain;

import com.noair.easip.post.domain.PostSchedule;
import com.noair.easip.util.BaseEntity;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "POST_SCHEDULE_NOTIFICATION")
@AllArgsConstructor
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = false)
@Builder
@Getter
public class PostScheduleNotification extends BaseEntity {
    @EmbeddedId
    private PostScheduleNotificationId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("postScheduleId")
    @JoinColumn(name = "POST_SCHEDULE_ID", nullable = false)
    private PostSchedule postSchedule;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("memberId")
    @JoinColumn(name = "MEMBER_ID", nullable = false)
    private Member member;
}
