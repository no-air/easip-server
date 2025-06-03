package com.noair.easip.house.exception;

import com.noair.easip.web.config.ErrorCode;
import com.noair.easip.web.exception.DomainException;

public class HouseNotFoundException extends DomainException {
    public HouseNotFoundException() {
        super(ErrorCode.HOUSE_NOT_FOUND);
    }
}
