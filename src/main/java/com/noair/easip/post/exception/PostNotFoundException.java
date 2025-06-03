package com.noair.easip.post.exception;

import com.noair.easip.web.config.ErrorCode;
import com.noair.easip.web.exception.DomainException;

public class PostNotFoundException extends DomainException {
    public PostNotFoundException() {
        super(ErrorCode.POST_NOT_FOUND);
    }
}
