package com.noair.easip.post.exception;

import com.noair.easip.web.config.ErrorCode;
import com.noair.easip.web.exception.DomainException;

import static com.noair.easip.web.controller.dto.ErrorType.ALERT;

public class PostNotFoundException extends DomainException {
    public PostNotFoundException() {
        super(ErrorCode.POST_NOT_FOUND, ALERT);
    }
}
