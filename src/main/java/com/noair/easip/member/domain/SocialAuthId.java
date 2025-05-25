package com.noair.easip.member.domain;

import com.noair.easip.auth.config.properties.SocialLoginProvider;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class SocialAuthId {
    @Column(name = "provider")
    private SocialLoginProvider provider;

    @Column(name = "identifier")
    private String identifier;
} 