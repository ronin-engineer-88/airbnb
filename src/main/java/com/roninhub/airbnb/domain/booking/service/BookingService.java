package com.roninhub.airbnb.domain.booking.service;

import com.roninhub.airbnb.domain.booking.constant.AvailabilityStatus;
import com.roninhub.airbnb.domain.booking.constant.BookingStatus;
import com.roninhub.airbnb.domain.booking.dto.request.BookingRequest;
import com.roninhub.airbnb.domain.booking.dto.response.BookingResponse;
import com.roninhub.airbnb.domain.booking.entity.Booking;
import com.roninhub.airbnb.domain.booking.mapper.BookingMapper;
import com.roninhub.airbnb.domain.booking.repository.BookingRepository;
import com.roninhub.airbnb.domain.common.constant.ResponseCode;
import com.roninhub.airbnb.domain.common.exception.BusinessException;
import com.roninhub.airbnb.domain.homestay.constant.HomestayStatus;
import com.roninhub.airbnb.domain.homestay.service.HomestayService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingService {

    private final BookingRepository bookingRepository;
    private final AvailabilityService availabilityService;
    private final HomestayService homestayService;
    private final PricingService pricingService;
    private final BookingMapper mapper;


    @SneakyThrows
    @Transactional
    public BookingResponse book(final BookingRequest request) {
        validateRequest(request);
        validateHomestay(request);

        final Long homestayId = request.getHomestayId();
        final LocalDate checkinDate = request.getCheckinDate();
        final LocalDate checkoutDate = request.getCheckoutDate();

        log.debug("[request_id={}] User user_id={} is acquiring lock homestay_id={} from checkin_date={} to checkout_date={}", request.getRequestId(), request.getUserId(), homestayId, checkinDate, checkoutDate);
        final var aDays = availabilityService.checkAvailabilityForBooking(homestayId, checkinDate, checkoutDate);
        log.debug("[request_id={}] User user_id={} locked homestay_id={} from checkin_date={} to checkout_date={}", request.getRequestId(), request.getUserId(), request.getHomestayId(), checkinDate, checkoutDate);

        Thread.sleep(5000);

        final var price = pricingService.calculate(aDays);
        final var booking = Booking.builder()
                .homestayId(homestayId)
                .userId(request.getUserId())
                .checkinDate(checkinDate)
                .checkoutDate(checkoutDate)
                .guests(request.getGuests())
                .subtotal(price.getSubtotal())
                .discount(price.getDiscount())
                .totalAmount(price.getTotalAmount())
                .currency(price.getCurrency())
                .note(request.getNote())
                .status(BookingStatus.BOOKED.getValue())
                .requestId(request.getRequestId())
                .build();

        aDays.forEach(a -> a.setStatus(AvailabilityStatus.BOOKED.getValue()));

        availabilityService.saveAll(aDays);
        bookingRepository.save(booking);
        log.info("[request_id={}] User user_id={} created booking_id={} successfully", request.getRequestId(), request.getUserId(), booking.getId());
        return mapper.toResponse(booking);
    }

    private void validateRequest(final BookingRequest request) {
        final var checkinDate = request.getCheckinDate();
        final var checkoutDate = request.getCheckoutDate();
        final var currentDate = LocalDate.now();

        if (checkinDate.isBefore(currentDate) || checkinDate.isAfter(checkoutDate)) {
            throw new BusinessException(ResponseCode.CHECKIN_DATE_INVALID);
        }

        if (request.getGuests() <= 0) {
            throw new BusinessException(ResponseCode.GUESTS_INVALID);
        }
    }

    private void validateHomestay(final BookingRequest request) {
        final var homestay = homestayService.getHomestayById(request.getHomestayId());
        if (homestay == null) {
            throw new BusinessException(ResponseCode.HOMESTAY_NOT_FOUND);
        }

        if (homestay.getStatus() != HomestayStatus.ACTIVE.getValue()) {
            throw new BusinessException(ResponseCode.HOMESTAY_NOT_ACTIVE);
        }

        if (homestay.getGuests() < request.getGuests()) {
            throw new BusinessException(ResponseCode.GUESTS_INVALID);
        }
    }
}
