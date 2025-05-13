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
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JWTAccessDeniedHandler implements AccessDeniedHandler {
    final ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.AUTHORIZATION_FAILED, ErrorType.ALERT);

        response.setCharacterEncoding("UTF-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));

        log.warn("[{}] {} {}", this.getClass().getSimpleName(), errorResponse.code(), errorResponse.message());
    }
}
