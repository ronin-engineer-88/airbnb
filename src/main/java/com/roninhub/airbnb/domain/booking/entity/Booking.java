package com.roninhub.airbnb.domain.booking.entity;


import java.util.Date;

public class Booking {

    private Long id;
    private Long homestayId;
    private Long userId;

    private Integer status;

    private Date checkin;
    private Date checkout;
}
