package com.noair.easip.batch.crawler;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CrawledPostHistoryRepository extends JpaRepository<CrawledPostHistory, String> {
    boolean existsByBoardId(String boardId);

    CrawledPostHistory findByBoardId(String boardId);
}
