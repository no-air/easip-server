package com.noair.easip.auth.exception;

import com.noair.easip.web.config.ErrorCode;
import lombok.Getter;
import org.springframework.security.core.AuthenticationException;

@Getter
public class TokenNotValidException extends AuthenticationException {
    private final ErrorCode errorCode;

    public TokenNotValidException() {
        super(ErrorCode.TOKEN_INVALID.getCode());
        this.errorCode = ErrorCode.TOKEN_INVALID;
    }

    public TokenNotValidException(ErrorCode errorCode) {
        super(errorCode.getCode()); // code만 넘김
        this.errorCode = errorCode;
    }
}
