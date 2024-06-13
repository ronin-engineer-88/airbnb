package com.roninhub.airbnb.domain.booking.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Date;


@Entity
@Table(name = "homestay_availability")
@RequiredArgsConstructor
@Getter
@Setter
@IdClass(HomestayAvailabilityId.class)
public class HomestayAvailability {

    @Id
    @Column(name = "homestay_id")
    private Long homestayId;

    @Id
    @Column(name = "date")
    private Date date;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "status")
    private Integer status;
}
