package com.noair.easip.web.exception;

import com.noair.easip.web.config.ErrorCode;
import com.noair.easip.web.controller.dto.ErrorType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DomainException extends RuntimeException {
    private final ErrorCode errorCode;
    private final ErrorType errorType;

    public DomainException(ErrorCode errorCode) {
        this(errorCode, ErrorType.NONE);
    }
}
