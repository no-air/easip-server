package com.noair.easip.web.config;

import com.noair.easip.web.config.properties.WebProperties;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        servers = {
                @Server(url = "https://dev.api.easip.kwon-server.cc/"),
                @Server(url = "http://localhost:8080/"),
        }
)
@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {
    final WebProperties webProperties;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("앱 엑세스 토큰", bearerAuth())
//                        .addSecuritySchemes("앱 버전 키", appKey())
                )
                .addSecurityItem(new SecurityRequirement()
                        .addList("앱 엑세스 토큰")
//                        .addList("앱 버전 키")
                );
    }

    private SecurityScheme bearerAuth() {
        return new SecurityScheme()
                .type(SecurityScheme.Type.APIKEY)
                .in(SecurityScheme.In.HEADER)
                .name(webProperties.headerNames().accessToken());
    }

    private SecurityScheme appKey() {
        return new SecurityScheme()
                .type(SecurityScheme.Type.APIKEY)
                .in(SecurityScheme.In.HEADER)
                .name(webProperties.headerNames().appKeyHeader());
    }
}
