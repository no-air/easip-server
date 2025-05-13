package com.noair.easip.member.domain;

import com.noair.easip.auth.config.properties.SocialLoginProvider;
import jakarta.persistence.*;
import lombok.*;

@Entity
@IdClass(SocialAuthKey.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@Getter
public class SocialAuth extends BaseEntity {
    @Id
    @Enumerated(EnumType.STRING)
    @Column(name = "provider")
    private SocialLoginProvider provider;

    @Id
    @Column(name = "identifier")
    private String identifier;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;
}
