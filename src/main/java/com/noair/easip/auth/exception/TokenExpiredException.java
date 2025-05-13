package com.noair.easip.auth.exception;

import com.noair.easip.web.ErrorCode;

public class TokenExpiredException extends TokenNotValidException {
    public TokenExpiredException() {
        super(ErrorCode.TOKEN_EXPIRED);
    }
}
