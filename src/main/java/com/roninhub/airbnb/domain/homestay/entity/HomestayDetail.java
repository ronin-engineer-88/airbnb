package com.roninhub.airbnb.domain.homestay.entity;


import jakarta.persistence.Column;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;


@RequiredArgsConstructor
@Getter
@Setter
public class HomestayDetail extends Homestay {

    @Column(name = "avg_price")
    private BigDecimal avgPrice;

    public HomestayDetail(Long id, String name, String description, BigDecimal avgPrice) {
        super(id, name, description);
        this.avgPrice = avgPrice;
    }
}
