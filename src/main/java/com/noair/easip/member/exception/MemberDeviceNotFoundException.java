package com.noair.easip.member.exception;

import com.noair.easip.web.exception.DomainException;

import static com.noair.easip.web.config.ErrorCode.MEMBER_DEVICE_NOT_FOUND;

public class MemberDeviceNotFoundException extends DomainException {
    public MemberDeviceNotFoundException() {
        super(MEMBER_DEVICE_NOT_FOUND);
    }
}
