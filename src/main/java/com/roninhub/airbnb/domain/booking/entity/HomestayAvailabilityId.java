package com.roninhub.airbnb.domain.booking.entity;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.sql.Date;


@RequiredArgsConstructor
@EqualsAndHashCode
public class HomestayAvailabilityId implements Serializable {

    private String homestayId;

    private Date date;

}
