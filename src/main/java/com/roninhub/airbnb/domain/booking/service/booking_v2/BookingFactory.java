package com.roninhub.airbnb.domain.booking.service.booking_v2;

import com.roninhub.airbnb.domain.booking.dto.request.BookingRequest;
import com.roninhub.airbnb.domain.common.constant.ResponseCode;
import com.roninhub.airbnb.domain.common.exception.BusinessException;
import com.roninhub.airbnb.domain.homestay.entity.ListingType;
import com.roninhub.airbnb.domain.homestay.service.HomestayService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookingFactory {   // Abstract Factory

    private final VillaBookingService villaBookingService;
    private HomestayService homestayService;
    private BaseBookingService baseBookingService;
    private ApartmentBookingService apartmentBookingService;


    public BookingService build(BookingRequest request) {
        var homestay = homestayService.getHomestayById(request.getHomestayId());
        if (homestay == null) {
            throw new BusinessException(ResponseCode.HOMESTAY_NOT_FOUND);
        }

        return switch (ListingType.of(homestay.getType())) {
            case APARTMENT -> apartmentBookingService;
            case VILLA -> villaBookingService;
            default -> baseBookingService;
        };
    }
}
