package com.noair.easip.web.controller;

import com.noair.easip.auth.config.TokenGenerator;
import com.noair.easip.auth.config.properties.TokenPair;
import com.noair.easip.auth.controller.dto.AuthResultResponse;
import com.noair.easip.batch.crawler.CrawlService;
import com.noair.easip.util.DefaultResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@Profile("!prod")
@RestController
@RequiredArgsConstructor
public class DevController {
    private final TokenGenerator tokenGenerator;
    private final CrawlService crawlService;

    @Operation(summary = "페이크 토큰 발행", description = "특정 회원의 개발용 페이크 인증 토큰을 발행합니다. 파라미터는 실제 회원 ID를 사용해야 합니다.")
    @PostMapping("/v1/auth/fake-token")
    AuthResultResponse generateFakeToken(
            @Parameter(example = "00000000000000000000000000")
            @RequestParam
            String memberId
    ) {
        TokenPair tokenPair = tokenGenerator.generateTokenPair(memberId);
        return AuthResultResponse.of(tokenPair, false);
    }

    @Operation(summary = "공고 크롤링 배치 실행")
    @PostMapping("/v1/crawl/posts")
    DefaultResponse crawlPostList() {
        if (crawlService.crawlPostList()) {
            return DefaultResponse.ok();
        } else {
            return DefaultResponse.fail();
        }
    }

    @Operation(summary = "특정 단일 공고 크롤링 배치 실행")
    @PostMapping("/v1/crawl/posts/{boardId}")
    DefaultResponse crawlPostDetail(
            @PathVariable
            String boardId
    ) {
        try {
            crawlService.insertNewPost(boardId, LocalDateTime.now());
            return DefaultResponse.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return DefaultResponse.fail();
        }
    }
}
