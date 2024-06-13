package com.roninhub.airbnb.domain.homestay.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class HomestayDetailDTO {

    private Long id;
    private String name;
    private String description;
    private Double avgPrice;

}
