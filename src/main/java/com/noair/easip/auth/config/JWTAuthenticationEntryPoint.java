package com.noair.easip.auth.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.noair.easip.web.config.ErrorCode;
import com.noair.easip.web.controller.dto.ErrorResponse;
import com.noair.easip.web.controller.dto.ErrorType;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JWTAuthenticationEntryPoint implements AuthenticationEntryPoint {
    final ObjectMapper objectMapper;


    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ErrorCode errorCode = ErrorCode.fromCode(authException.getMessage());
        ErrorResponse errorResponse = ErrorResponse.of(errorCode, ErrorType.ALERT);

        response.setCharacterEncoding("UTF-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));

        log.warn("[{}] {} {}", this.getClass().getSimpleName(), errorResponse.code(), errorResponse.message());
    }
}
