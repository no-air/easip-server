package com.noair.easip.auth.config.properties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@Getter
public class FcmCredential {
    @Value("${fcm.api-url}")
    String API_URL = "<https://fcm.googleapis.com/v1/projects/easip-2c0a1/messages:send>";



}
