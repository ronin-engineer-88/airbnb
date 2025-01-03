package com.roninhub.airbnb.domain.booking.service_v2.booking;

import com.roninhub.airbnb.domain.booking.dto.request.BookingRequest;
import com.roninhub.airbnb.domain.booking.entity.Booking;
import com.roninhub.airbnb.domain.homestay.entity.Homestay;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApartmentBookingService extends BaseBookingService {

    @Override
    protected Homestay validateHomestay(final BookingRequest request) {
        log.info("Checking the status of the building...");
        return super.validateHomestay(request);
    }

    @Override
    protected void sendNotifications(Booking booking) {
        super.sendNotifications(booking);
        log.info("Sending email to the building...");
    }
}
