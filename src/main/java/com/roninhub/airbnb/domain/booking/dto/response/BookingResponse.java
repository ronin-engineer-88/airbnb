package com.roninhub.airbnb.domain.booking.dto.response;

import com.roninhub.airbnb.domain.payment.dto.response.InitPaymentResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingResponse {

    BookingDto booking;

    InitPaymentResponse payment;
}
