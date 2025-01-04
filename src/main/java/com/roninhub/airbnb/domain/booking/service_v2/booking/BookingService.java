package com.roninhub.airbnb.domain.booking.service_v2.booking;

import com.roninhub.airbnb.domain.booking.dto.request.BookingRequest;
import com.roninhub.airbnb.domain.booking.dto.response.BookingResponse;

public interface BookingService {
    BookingResponse execute(BookingRequest request);
}
