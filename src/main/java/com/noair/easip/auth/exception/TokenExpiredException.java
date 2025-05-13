package com.noair.easip.auth.exception;

import com.noair.easip.web.config.ErrorCode;

public class TokenExpiredException extends TokenNotValidException {
    public TokenExpiredException() {
        super(ErrorCode.TOKEN_EXPIRED);
    }
}
