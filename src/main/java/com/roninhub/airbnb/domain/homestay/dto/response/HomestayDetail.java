package com.roninhub.airbnb.domain.homestay.dto.response;


import com.roninhub.airbnb.domain.homestay.entity.Homestay;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;


@RequiredArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class HomestayDetail {

    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "type")
    private Integer type;

    @Column(name = "status")
    private Integer status;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "address")
    private String address;

    @Column(name = "guests")
    private Integer guests;

    @Column(name = "bedrooms")
    private Integer bedrooms;

    @Column(name = "bathrooms")
    private Integer bathrooms;

    @Column(name = "version")
    private Long version;

    @Column(name = "avg_price")
    private Double avgPrice;

}
