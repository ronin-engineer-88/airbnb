package com.roninhub.airbnb.domain.homestay.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum AvailabilityStatus {

    AVAILABLE(0),
    BOOKED(1),
    SERVED(2)
    ;


    private final int value;
}
