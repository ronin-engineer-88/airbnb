package com.roninhub.airbnb.domain.booking.dto.response;


import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.Column;
import lombok.*;

import java.math.BigDecimal;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class BookingResponse {
    private String requestId;
    private Long userId;
    private Long homestayId;
    private String checkinDate;
    private String checkoutDate;
    private Integer guests;

    private Integer status;

    private BigDecimal subtotal;
    private BigDecimal discount;
    private BigDecimal totalAmount;
    private String currency;

    private String note;
}
