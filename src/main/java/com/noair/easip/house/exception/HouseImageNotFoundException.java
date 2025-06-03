package com.noair.easip.house.exception;

import com.noair.easip.web.config.ErrorCode;
import com.noair.easip.web.exception.DomainException;

public class HouseImageNotFoundException extends DomainException {
    public HouseImageNotFoundException() {
        super(ErrorCode.HOUSE_IMAGE_NOT_FOUND);
    }
}
