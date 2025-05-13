package com.noair.easip.member.domain;

import com.noair.easip.auth.config.properties.SocialLoginProvider;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class SocialAuthKey implements Serializable {
    private SocialLoginProvider provider;
    private String identifier;
}
