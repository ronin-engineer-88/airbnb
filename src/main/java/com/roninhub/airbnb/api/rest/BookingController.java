package com.roninhub.airbnb.api.rest;

import com.roninhub.airbnb.domain.booking.dto.request.BookingRequest;
import com.roninhub.airbnb.domain.booking.dto.response.BookingResponse;
import com.roninhub.airbnb.domain.booking.service.booking_v0.BookingV0Service;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/bookings")
@RequiredArgsConstructor
@Slf4j
public class BookingController {

    private final BookingV0Service service;

    @PostMapping
    BookingResponse bookHomestay(@Valid @RequestBody BookingRequest request) {
        log.info("Booking Request: {}", request);
        return service.book(request);
    }
}
