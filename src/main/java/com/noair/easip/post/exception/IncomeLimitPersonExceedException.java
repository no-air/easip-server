package com.noair.easip.post.exception;

import com.noair.easip.web.exception.DomainException;

import static com.noair.easip.web.config.ErrorCode.POST_INCOME_LIMIT_PERSON_EXCEEDED;

public class IncomeLimitPersonExceedException extends DomainException {
    public IncomeLimitPersonExceedException() {
        super(POST_INCOME_LIMIT_PERSON_EXCEEDED);
    }
}
