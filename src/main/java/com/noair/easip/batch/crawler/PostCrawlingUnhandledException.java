package com.noair.easip.batch.crawler;

import com.noair.easip.web.exception.DomainException;

import static com.noair.easip.web.config.ErrorCode.POST_CRAWLING_UNHANDLED_EXCEPTION;

public class PostCrawlingUnhandledException extends DomainException {
    public PostCrawlingUnhandledException() {
        super(POST_CRAWLING_UNHANDLED_EXCEPTION);
    }
}
