package com.noair.easip.web.config;

import com.noair.easip.auth.config.LoginMemberIdArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SpringWebConfig implements WebMvcConfigurer {
    final WebRequestInterceptor webRequestInterceptor;
    final LoginMemberIdArgumentResolver loginMemberIdArgumentResolver;
    // final AppKeyResolver appKeyResolver;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(webRequestInterceptor);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        // resolvers.add(appKeyResolver);
        resolvers.add(loginMemberIdArgumentResolver);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(
                        "http://localhost:3000",
                        "http://localhost:5173",
                        "https://kwon-server.cc",
                        "https://easip.kwon-server.cc",
                        "https://dev-api.easip.kwon-server.cc",
                        "https://prod-api.easip.kwon-server.cc",
                        "https://link.easip.kwon-server.cc",
                        "https://easip-web.pages.dev",
                        "https://easip-web-dev.pages.dev",
                        "https://easip-web-prod.pages.dev"
                )
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowCredentials(true)
                .maxAge(3600);
    }
}
