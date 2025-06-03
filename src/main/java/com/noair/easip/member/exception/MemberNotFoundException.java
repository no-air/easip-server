package com.noair.easip.member.exception;

import com.noair.easip.web.exception.DomainException;

import static com.noair.easip.web.config.ErrorCode.*;
import static com.noair.easip.web.controller.dto.ErrorType.ALERT;

public class MemberNotFoundException extends DomainException {
    public MemberNotFoundException() {
        super(MEMBER_NOT_FOUND, ALERT);
    }
}
