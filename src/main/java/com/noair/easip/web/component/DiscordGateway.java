package com.noair.easip.web.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.noair.easip.web.config.properties.ExternalUrlProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DiscordGateway {
    private final ExternalUrlProperties externalUrlProperties;
    private final ObjectMapper objectMapper;

    @SneakyThrows
    public void sendDevAlertDiscord(DiscordWebhookDto dto) {
        sendWebhook(dto, externalUrlProperties.devAlertDiscordWebhook());
    }

    @SneakyThrows
    public void sendProdAlertDiscord(DiscordWebhookDto dto) {
        sendWebhook(dto, externalUrlProperties.prodAlertDiscordWebhook());
    }

    @SneakyThrows
    public void sendWebhook(DiscordWebhookDto dto, String webhookUrl) {
        List<String> chunks = splitContent(dto.getContent(), 1500);
        RestTemplate restTemplate = new RestTemplate();
        for (String chunk : chunks) {
            DiscordWebhookDto chunkDto = DiscordWebhookDto.builder()
                    .content(chunk)
                    .build();
            String body = objectMapper.writeValueAsString(chunkDto);
            RequestEntity<String> request = RequestEntity
                    .post(URI.create(webhookUrl))
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(body);
            restTemplate.exchange(request, Void.class);
        }
    }

    /**
     * 주어진 content를 maxLength 기준으로 분할.
     * 줄바꿈(\n) 위치를 우선으로, 없으면 강제 split.
     * 각 파트를 Markdown code fence로 감싼다.
     */
    private List<String> splitContent(String content, int maxLength) {
        List<String> rawParts = new ArrayList<>();
        String remaining = content;
        while (remaining.length() > maxLength) {
            int splitPos = remaining.lastIndexOf("\n", maxLength);
            if (splitPos <= 0) splitPos = maxLength;
            rawParts.add(remaining.substring(0, splitPos));
            remaining = remaining.substring(splitPos);
        }
        if (!remaining.isEmpty()) {
            rawParts.add(remaining);
        }
        // Wrap with code fences
        List<String> parts = new ArrayList<>();
        for (int i = 0; i < rawParts.size(); i++) {
            String chunk = rawParts.get(i);
            if (i == 0) {
                parts.add(chunk + "```\n");
            } else {
                parts.add("```json\n" + chunk + "```\n");
            }
        }
        // Append divider lines to the last chunk
        if (!parts.isEmpty()) {
            int lastIndex = parts.size() - 1;
            String lastChunk = parts.get(lastIndex) + "...\n...\n...\n";
            parts.set(lastIndex, lastChunk);
        }
        return parts;
    }

    @Builder
    @Getter
    public static class DiscordWebhookDto {
        private final String content;
        private final List<?> embeds = Collections.emptyList();
    }
}
