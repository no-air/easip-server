package com.noair.easip.member.controller.dto;

import com.noair.easip.auth.config.properties.SocialLoginProvider;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateUserDto(
        SocialLoginProvider provider,

        String identifier,

        @NotBlank
        @Size(min = 1)
        String name
) {
}
