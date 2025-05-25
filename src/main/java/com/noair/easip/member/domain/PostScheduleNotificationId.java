package com.noair.easip.member.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class PostScheduleNotificationId {
    @Column(name = "POST_SCHEDULE_ID")
    private String postScheduleId;

    @Column(name = "MEMBER_ID")
    private String memberId;
} 