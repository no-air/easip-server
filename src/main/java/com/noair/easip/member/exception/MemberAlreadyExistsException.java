package com.noair.easip.member.exception;

import com.noair.easip.web.config.ErrorCode;
import com.noair.easip.web.exception.DomainException;

public class MemberAlreadyExistsException extends DomainException {
    public MemberAlreadyExistsException() {
        super(ErrorCode.MEMBER_ALREADY_EXISTS);
    }
}
