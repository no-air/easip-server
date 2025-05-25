package com.noair.easip.member.domain;

import com.noair.easip.util.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "SOCIAL_AUTH")
@AllArgsConstructor
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = false)
@Builder
@Getter
public class SocialAuth extends BaseEntity {
    @EmbeddedId
    private SocialAuthId id;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;
}