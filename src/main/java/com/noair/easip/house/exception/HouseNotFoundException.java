package com.noair.easip.house.exception;

import com.noair.easip.web.exception.DomainException;

import static com.noair.easip.web.config.ErrorCode.*;
import static com.noair.easip.web.controller.dto.ErrorType.ALERT;

public class HouseNotFoundException extends DomainException {
    public HouseNotFoundException() {
        super(HOUSE_NOT_FOUND, ALERT);
    }
}
