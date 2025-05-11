package com.noair.easip.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.Arrays;

@RequiredArgsConstructor
@Configuration
public class SpringSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                // 세션을 생성하지 않고 기존 세션도 사용하지 않음 (JWT 사용)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // TODO : JWT 추가시 활성화
                /*
                .authorizeHttpRequests(requestsManagement -> {
                    RequestMatcher[] whitelistedMatchers = Arrays.stream(webProperties.urlWhitelists())
                            .map(AntPathRequestMatcher::new)
                            .toArray(AntPathRequestMatcher[]::new);

                    requestsManagement
                            .requestMatchers(whitelistedMatchers).permitAll()
                            .anyRequest().authenticated();
                })
                .addFilterBefore(authenticationHandler, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(appVersionFilter, JwtAuthenticationHandler.class)
                 */
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().permitAll()
                )
                .build();
    }
}
