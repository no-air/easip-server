package com.noair.easip.batch.crawler;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface PostCrawlingHistoryRepository extends JpaRepository<PostCrawlingHistory, LocalDateTime> {
}
