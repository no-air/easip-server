package com.noair.easip.member.exception;

import com.noair.easip.web.config.ErrorCode;
import com.noair.easip.web.exception.DomainException;

public class MemberNotFoundException extends DomainException {
    public MemberNotFoundException() {
        super(ErrorCode.MEMBER_NOT_FOUND);
    }
}
