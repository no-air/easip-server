package com.noair.easip.batch.crawler;

import com.noair.easip.web.exception.DomainException;

import static com.noair.easip.web.config.ErrorCode.POST_CRAWLING_PARSING_ERROR;

public class PostCrawlingParsingException extends DomainException {
    public PostCrawlingParsingException() {
        super(POST_CRAWLING_PARSING_ERROR);
    }
}
