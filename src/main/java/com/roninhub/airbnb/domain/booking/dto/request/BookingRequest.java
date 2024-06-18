package com.roninhub.airbnb.domain.booking.dto.request;


import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;



@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class BookingRequest {
    private String requestId;
    private Long userId;
    private Long homestayId;
    private String checkinDate;
    private String checkoutDate;
    private Integer guests;
    private String note;
}
