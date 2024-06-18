package com.roninhub.airbnb.domain.homestay.dto.request;


import com.roninhub.airbnb.domain.booking.constant.AvailabilityStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HomestaySearchRequest {

    private String checkInDate;
    private String checkOutDate;
    private Integer guests;
    private Integer wardId;
    private Integer districtId;
    private Integer cityId;
    private Integer stateId;
    private AvailabilityStatus status;

}
