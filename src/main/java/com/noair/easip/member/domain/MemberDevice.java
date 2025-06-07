package com.noair.easip.member.domain;

import com.noair.easip.util.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = false)
@Builder
@Getter
public class MemberDevice extends BaseEntity {
    @Id
    @Column(name = "FCM_TOKEN", nullable = false)
    private String fcmToken;

    @OneToOne
    @JoinColumn(name = "MEMBER_ID", nullable = false)
    private Member member;

    @Column(nullable = false)
    private Integer fcmTransmissionFailCnt = 0;

    private LocalDateTime fcmTransmissionFailDateTime;

    public void recordFcmTransmissionFail() {
        this.fcmTransmissionFailCnt++;
        this.fcmTransmissionFailDateTime = LocalDateTime.now();
    }
}
