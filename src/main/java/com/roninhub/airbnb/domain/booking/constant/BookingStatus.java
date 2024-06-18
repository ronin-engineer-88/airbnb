package com.roninhub.airbnb.domain.booking.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BookingStatus {

    DRAFT(0),
    BOOKED(1),
    CANCELLED(2),
    COMPLETED(3)
    ;

    private final int value;
}
