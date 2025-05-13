package com.noair.easip.auth.config;

import com.noair.easip.auth.config.properties.Token;
import com.noair.easip.auth.config.properties.TokenType;
import com.noair.easip.web.config.properties.WebProperties;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JWTAuthenticationHandler extends OncePerRequestFilter {
    final WebProperties webProperties;
    final TokenGenerator tokenGenerator;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = request.getHeader(webProperties.headerNames().accessToken());
        if (accessToken == null) {
            filterChain.doFilter(request, response);
            return;
        }

        Token token = tokenGenerator.extractTokenData(accessToken);
        Authentication authentication = new JWTTokenAuthentication(token, token.userId(), token.tokenType() == TokenType.TEMPORARY);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }
}
