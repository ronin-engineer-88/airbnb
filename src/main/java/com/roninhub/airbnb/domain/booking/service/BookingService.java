package com.roninhub.airbnb.domain.booking.service;

import com.roninhub.airbnb.domain.booking.constant.AvailabilityStatus;
import com.roninhub.airbnb.domain.booking.constant.BookingStatus;
import com.roninhub.airbnb.domain.booking.dto.request.BookingRequest;
import com.roninhub.airbnb.domain.booking.dto.response.BookingResponse;
import com.roninhub.airbnb.domain.booking.entity.Booking;
import com.roninhub.airbnb.domain.booking.mapper.BookingMapper;
import com.roninhub.airbnb.domain.booking.repository.BookingRepository;
import com.roninhub.airbnb.domain.booking.repository.HomestayAvailabilityRepository;
import com.roninhub.airbnb.domain.common.constant.ResponseCode;
import com.roninhub.airbnb.domain.common.exception.BusinessException;
import com.roninhub.airbnb.domain.homestay.constant.HomestayStatus;
import com.roninhub.airbnb.domain.homestay.service.HomestayService;
import com.roninhub.airbnb.infrastructure.util.DateUtil;
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

    private static final int NIGHT_MAX = 365;

    private final BookingRepository bookingRepository;
    private final HomestayAvailabilityRepository availabilityRepository;
    private final HomestayService homestayService;
    private final PricingService pricingService;
    private final BookingMapper mapper;


    @SneakyThrows
    @Transactional
    public BookingResponse book(final BookingRequest request) {
        validateRequest(request);
        validateHomestay(request);

        // check availability
        final Long homestayId = request.getHomestayId();
        final LocalDate checkinDate = request.getCheckinDate();
        final LocalDate checkoutDate = request.getCheckoutDate();
        final int nights = (int) DateUtil.getDiffInDays(checkinDate, checkoutDate);
        if (nights > NIGHT_MAX) {
            throw new BusinessException(ResponseCode.NIGHTS_INVALID);
        }

        log.debug("[request_id={}] User user_id={} is acquiring lock homestay_id={} from checkin_date={} to checkout_date={}", request.getRequestId(), request.getUserId(), homestayId, checkinDate, checkoutDate);
        final var aDays = availabilityRepository.findAndLockHomestayAvailability(
                request.getHomestayId(),
                AvailabilityStatus.AVAILABLE.getValue(),
                checkinDate,
                checkoutDate.minusDays(1)
        );
        log.debug("[request_id={}] User user_id={} locked homestay_id={} from checkin_date={} to checkout_date={}", request.getRequestId(), request.getUserId(), homestayId, checkinDate, checkoutDate);
        if (aDays.isEmpty() || aDays.size() < nights) {
            throw new BusinessException(ResponseCode.HOMESTAY_BUSY);
        }

        final var price = pricingService.calculate(aDays);
        // build booking
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

        availabilityRepository.saveAll(aDays);
        bookingRepository.save(booking);

        // Notify
        log.info("Sending email to user={}", booking.getUserId());

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

        if (request.getGuests() > homestay.getGuests() || request.getGuests() <= 0) {
            throw new BusinessException(ResponseCode.GUESTS_INVALID);
        }
    }
}
