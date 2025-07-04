package com.noair.easip.web.config;

import com.noair.easip.auth.config.JWTAccessDeniedHandler;
import com.noair.easip.auth.config.JWTAuthenticationEntryPoint;
import com.noair.easip.auth.config.JWTAuthenticationHandler;
import com.noair.easip.web.config.properties.WebProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.Arrays;

@RequiredArgsConstructor
@Configuration
public class SpringSecurityConfig {
    /**
     * JWT 엑세스 토큰을 통한 인증 처리기
     */
    final JWTAuthenticationHandler authenticationHandler;
    final JWTAccessDeniedHandler accessDeniedHandler;
    final JWTAuthenticationEntryPoint authenticationEntryPoint;

    final WebProperties webProperties;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()))
                // 세션을 생성하지 않고 기존 세션도 사용하지 않음 (JWT 사용)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(requestsManagement -> {
                    RequestMatcher[] whitelistedMatchers = Arrays.stream(webProperties.urlWhitelists())
                            .map(AntPathRequestMatcher::new)
                            .toArray(AntPathRequestMatcher[]::new);

                    requestsManagement
                            .requestMatchers(whitelistedMatchers).permitAll()
                            .anyRequest().authenticated();
                })
                .addFilterBefore(authenticationHandler, AuthorizationFilter.class)
//                .addFilterBefore(appVersionFilter, JwtAuthenticationHandler.class)
                .exceptionHandling(exceptionHandlerManagement ->
                        exceptionHandlerManagement
                                .authenticationEntryPoint(authenticationEntryPoint)
                                .accessDeniedHandler(accessDeniedHandler)
                )
                .build();
    }
}
