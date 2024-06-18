package com.roninhub.airbnb.domain.booking.mapper;

import com.roninhub.airbnb.domain.booking.dto.response.BookingResponse;
import com.roninhub.airbnb.domain.booking.entity.Booking;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface BookingMapper {

//    BookingMapper INSTANCE = Mappers.getMapper(BookingMapper.class);


    BookingResponse toResponse(Booking booking);
}
