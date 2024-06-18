package com.roninhub.airbnb.domain.booking.entity;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;



@Entity
@Table(name = "booking")
@RequiredArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "homestay_id")
    private Long homestayId;

    @Column(name = "checkin_date")
    private LocalDate checkinDate;

    @Column(name = "checkout_date")
    private LocalDate checkoutDate;

    @Column(name = "guests")
    private Integer guests;

    @Column(name = "status")
    private Integer status;

    @Column(name = "currency")
    private String currency;

    @Column(name = "subtotal")
    private BigDecimal subtotal;

    @Column(name = "discount")
    private BigDecimal discount;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @Column(name = "note")
    private String note;

    @Column(name = "request_id")
    private String requestId;
}
