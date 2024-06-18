package com.roninhub.airbnb.domain.booking.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum AvailabilityStatus {

    AVAILABLE(0),
    HELD(1),
    BOOKED(2),
    SERVED(3)
    ;


    private final int value;
}
