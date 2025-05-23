package com.noair.easip.auth.config;

import com.noair.easip.auth.config.properties.Token;
import com.noair.easip.auth.config.properties.TokenType;
import com.noair.easip.auth.exception.TokenNotValidException;
import com.noair.easip.web.config.ErrorCode;
import com.noair.easip.web.config.properties.WebProperties;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class JWTAuthenticationHandler extends OncePerRequestFilter {
    final WebProperties webProperties;
    final TokenGenerator tokenGenerator;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return Arrays.stream(webProperties.urlWhitelists())
                .map(AntPathRequestMatcher::new)
                .anyMatch(matcher -> matcher.matches(request));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException, TokenNotValidException {
        String accessToken = request.getHeader(webProperties.headerNames().accessToken());
        if (accessToken == null) {
            throw new TokenNotValidException(ErrorCode.TOKEN_REQUIRED);
        }

        Token token = tokenGenerator.extractTokenData(accessToken);
        Authentication authentication = new JWTTokenAuthentication(token, token.userId(), token.tokenType() == TokenType.TEMPORARY);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }
}
