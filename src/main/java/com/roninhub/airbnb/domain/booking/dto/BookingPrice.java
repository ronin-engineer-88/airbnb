package com.roninhub.airbnb.domain.booking.dto;


import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingPrice {
    private BigDecimal subtotal;
    private BigDecimal discount;
    private BigDecimal totalAmount;
    private String currency;
}
