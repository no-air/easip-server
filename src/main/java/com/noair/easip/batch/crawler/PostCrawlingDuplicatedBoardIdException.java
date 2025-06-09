package com.noair.easip.batch.crawler;

import com.noair.easip.web.exception.DomainException;

import static com.noair.easip.web.config.ErrorCode.POST_CRAWLING_DUPLICATED_BOARD_ID;

public class PostCrawlingDuplicatedBoardIdException extends DomainException {
    public PostCrawlingDuplicatedBoardIdException() {
        super(POST_CRAWLING_DUPLICATED_BOARD_ID);
    }
}
