package com.noair.easip.batch.crawler;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CrawlScheduler {
    private final CrawlService crawlService;

    @Scheduled(cron = "0 0 21 * * *") // Every day at 21:00
    public void schedulePostCrawlJob() {
        crawlService.crawlPostList();
    }
}
