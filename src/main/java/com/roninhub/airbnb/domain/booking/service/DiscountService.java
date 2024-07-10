package com.roninhub.airbnb.domain.booking.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Service
@Slf4j
public class DiscountService {
    private static final Integer LONG_STAY = 3;
    private static final BigDecimal LONG_STAY_DISCOUNT_RATE = BigDecimal.valueOf(-0.05);


    // Return a negative amount
    public BigDecimal getDiscountAmount(BigDecimal subtotal, int nights) {
        BigDecimal discount = BigDecimal.ZERO;

        if (nights >= LONG_STAY) {
            discount = subtotal.multiply(LONG_STAY_DISCOUNT_RATE);
        }

        return discount;
    }
}

