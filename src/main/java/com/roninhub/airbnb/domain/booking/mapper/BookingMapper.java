package com.roninhub.airbnb.domain.booking.mapper;

import com.roninhub.airbnb.domain.booking.dto.response.BookingResponse;
import com.roninhub.airbnb.domain.booking.entity.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface BookingMapper {

    @Mapping(source = "id", target = "bookingId")
    BookingResponse toResponse(Booking booking);
}
