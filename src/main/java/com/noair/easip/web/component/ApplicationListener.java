package com.noair.easip.web.component;

import com.noair.easip.web.controller.dto.ErrorReportDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.noair.easip.web.component.DiscordGateway.*;

@Component
@RequiredArgsConstructor
public class ApplicationListener {
    final Environment environment;
    final DiscordGateway discordGateway;

    private boolean isOperatingServer() {
        List<String> activeProfiles = List.of(environment.getActiveProfiles());

        return !activeProfiles.isEmpty() && (activeProfiles.contains("prod") || activeProfiles.contains("dev"));
    }

    private boolean isDevServer() { return List.of(environment.getActiveProfiles()).contains("dev"); }

    private boolean isProdServer() { return List.of(environment.getActiveProfiles()).contains("prod"); }

    @Async
    @EventListener
    public void onUnhandledErrorReported(ErrorReportDto errorReportDto) {
        if (!isOperatingServer()) return;

        String errorMessage = errorReportDto.message() == null
                ? "알 수 없는 에러"
                : errorReportDto.message();

        // 1. 메시지 본문(build with Markdown)
        StringBuilder sb = new StringBuilder();
        sb.append("**백엔드 서버에서 핸들링되지 않은 오류가 발생했습니다**\n")
                .append("**에러 메시지:** ").append(errorMessage).append("\n\n")
                .append("**에러 페이로드:**\n")
                .append("```json\n")
                .append(errorReportDto.payload())
                .append("\n```\n");

        DiscordWebhookDto dto = DiscordWebhookDto.builder()
                .content(sb.toString())
                .build();

        if (isProdServer()) discordGateway.sendProdAlertDiscord(dto);
        if (isDevServer()) discordGateway.sendDevAlertDiscord(dto);
    }
}
