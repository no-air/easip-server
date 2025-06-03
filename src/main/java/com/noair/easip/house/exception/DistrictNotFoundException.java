package com.noair.easip.house.exception;

import com.noair.easip.web.exception.DomainException;

import static com.noair.easip.web.config.ErrorCode.DISTRICT_NOT_FOUND;

public class DistrictNotFoundException extends DomainException {
    public DistrictNotFoundException() {
        super(DISTRICT_NOT_FOUND);
    }
}
