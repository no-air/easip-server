package com.noair.easip.post.exception;

import com.noair.easip.web.exception.DomainException;

import static com.noair.easip.web.config.ErrorCode.POST_SCHEDULE_NOT_FOUND;
import static com.noair.easip.web.controller.dto.ErrorType.ALERT;

public class PostScheduleNotFoundException extends DomainException {
    public PostScheduleNotFoundException() {
        super(POST_SCHEDULE_NOT_FOUND, ALERT);
    }
}
