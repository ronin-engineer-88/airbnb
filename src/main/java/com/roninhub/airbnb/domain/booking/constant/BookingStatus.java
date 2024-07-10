package com.roninhub.airbnb.domain.booking.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BookingStatus {

    DRAFT(0),
    BOOKED(1),
    COMPLETED(2),
    CANCELLED(3)
    ;

    private final int value;
}
