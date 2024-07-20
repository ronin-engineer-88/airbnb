package com.roninhub.airbnb.domain.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResponseCode {

    SUCCESS("SUCCESS", 200),
    INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR", 500),
    BAD_REQUEST("BAD_REQUEST", 400),
    INVALID_PARAMS("INVALID_PARAMS", 400),

    HOMESTAY_NOT_FOUND("HOMESTAY_NOT_FOUND", 404),
    HOMESTAY_NOT_ACTIVE("HOMESTAY_NOT_ACTIVE", 404),

    GUESTS_INVALID("GUESTS_INVALID", 400),
    CHECKIN_DATE_INVALID("CHECKIN_DATE_INVALID", 400),
    NIGHTS_INVALID("NIGHTS_INVALID", 400),
    HOMESTAY_BUSY("HOMESTAY_BUSY", 400),

    VNPAY_SIGNING_FAILED("VNPAY_SIGNING_FAILED", 400),
    ;

    private final String type;
    private final Integer code;
}
