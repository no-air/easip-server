package com.noair.easip.web.component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.noair.easip.batch.crawler.GptAnswerDeserializationException;
import com.noair.easip.post.controller.dto.PostFlatDto;
import com.noair.easip.post.controller.dto.PostHouseFlatDto;
import com.noair.easip.post.controller.dto.PostScheduleFlatDto;
import com.noair.easip.web.config.properties.GptAgentProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

import static com.noair.easip.util.GptPromptGenerator.*;

@Component
@Slf4j
public class GptGateway {
    private final ObjectMapper objectMapper;
    private final GptAgentProperties gptAgentProperties;
    private final WebClient webClient;

    public GptGateway(GptAgentProperties gptAgentProperties, ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.gptAgentProperties = gptAgentProperties;
        this.webClient = WebClient.builder()
                .baseUrl("https://api.openai.com/v1/")
                .defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader("Authorization", "Bearer " + gptAgentProperties.apiKey())
                .build();
    }

    public String askWithPdf(String developerMsg, String userMsg, String pdfName, String pdfBase64) {
        return webClient.post()
                .uri("responses")
                .bodyValue(
                        Map.of(
                                "model", "gpt-4.1-mini-2025-04-14",
                                "temperature", 1.0,
                                "max_output_tokens", 10240,
                                "input", List.of(
                                        Map.of(
                                                "role", "developer",
                                                "content", developerMsg
                                        ),
                                        Map.of(
                                                "role", "user",
                                                "content", List.of(
                                                        Map.of(
                                                                "type", "input_file",
                                                                "filename", pdfName,
                                                                "file_data", "data:application/pdf;base64," + pdfBase64
                                                        ),
                                                        Map.of(
                                                                "type", "input_text",
                                                                "text", userMsg
                                                        )
                                                )
                                        )
                                )
                        )

                )
                .retrieve()
                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                        clientResponse -> clientResponse.bodyToMono(String.class)
                                .doOnNext(errorBody -> log.error("Error Body: " + errorBody))
                                .flatMap(errorBody -> {
                                    log.error("Status: " + clientResponse.statusCode());
                                    return Mono.error(new RuntimeException("API Error: " + errorBody));
                                })
                )
                .bodyToMono(String.class)
                .doOnNext(body -> System.out.println("Body: " + body))
                .block();
    }

    public List<PostHouseFlatDto> askPostHouses(String pdfName, String pdfBase64) {
        String response = askWithPdf(POST_HOUSE_DEVELOPER_MSG, POST_HOUSE_USER_MSG, pdfName, pdfBase64);

        try {
            JsonNode jsonNode = objectMapper.readTree(response);
            String answer = jsonNode
                    .get("output").get(0)
                    .get("content").get(0)
                    .get("text").asText()
                    .replaceAll("(?s)```json\\s*|```", "").trim(); // text에서 ```json ... ``` 감싸진 코드 블럭 제거

            return objectMapper.readValue(
                    answer,
                    new TypeReference<>() {
                    }
            );
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new GptAnswerDeserializationException();
        }
    }

    public PostFlatDto askPost(String pdfName, String pdfBase64) {
        String response = askWithPdf(POST_DEVELOPER_MSG, POST_USER_MSG, pdfName, pdfBase64);

        try {
            JsonNode jsonNode = objectMapper.readTree(response);
            String answer = jsonNode
                    .get("output").get(0)
                    .get("content").get(0)
                    .get("text").asText()
                    .replaceAll("(?s)```json\\s*|```", "").trim(); // text에서 ```json ... ``` 감싸진 코드 블럭 제거

            return objectMapper.readValue(
                    answer,
                    PostFlatDto.class
            );
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new GptAnswerDeserializationException();
        }
    }

    public List<PostScheduleFlatDto> askPostSchedules(String pdfName, String pdfBase64) {
        String response = askWithPdf(POST_SCHEDULE_DEVELOPER_MSG, POST_SCHEDULE_USER_MSG, pdfName, pdfBase64);

        try {
            JsonNode jsonNode = objectMapper.readTree(response);
            String answer = jsonNode
                    .get("output").get(0)
                    .get("content").get(0)
                    .get("text").asText()
                    .replaceAll("(?s)```json\\s*|```", "").trim(); // text에서 ```json ... ``` 감싸진 코드 블럭 제거

            return objectMapper.readValue(
                    answer,
                    new TypeReference<>() {
                    }
            );
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new GptAnswerDeserializationException();
        }
    }
}
