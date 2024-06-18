package com.roninhub.airbnb.domain.booking.entity;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;


@RequiredArgsConstructor
@EqualsAndHashCode
public class HomestayAvailabilityId implements Serializable {

    private Long homestayId;

    private LocalDate date;

}
