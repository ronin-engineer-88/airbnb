package com.roninhub.airbnb.domain.booking.service;

import com.roninhub.airbnb.domain.booking.constant.AvailabilityStatus;
import com.roninhub.airbnb.domain.booking.constant.BookingStatus;
import com.roninhub.airbnb.domain.booking.constant.Currency;
import com.roninhub.airbnb.domain.booking.dto.request.BookingRequest;
import com.roninhub.airbnb.domain.booking.dto.response.BookingResponse;
import com.roninhub.airbnb.domain.booking.entity.Booking;
import com.roninhub.airbnb.domain.booking.mapper.BookingMapper;
import com.roninhub.airbnb.domain.booking.repository.BookingRepository;
import com.roninhub.airbnb.domain.booking.repository.HomestayAvailabilityRepository;
import com.roninhub.airbnb.domain.common.constant.ResponseCode;
import com.roninhub.airbnb.domain.common.exception.BusinessException;
import com.roninhub.airbnb.domain.homestay.service.HomestayService;
import com.roninhub.airbnb.infrastructure.util.DateUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingService {

    private static final int NIGHT_MAX = 365;

    private final BookingRepository bookingRepository;
    private final HomestayAvailabilityRepository availabilityRepository;
    private final HomestayService homestayService;
    private final DiscountService discountService;
    private final BookingMapper mapper;


    @Transactional
    public BookingResponse book(BookingRequest request) {
        var homestay = homestayService.getHomestayById(request.getHomestayId());
        if (homestay == null) {
            throw new BusinessException(ResponseCode.HOMESTAY_NOT_FOUND);
        }

        if (request.getGuests() > homestay.getGuests() || request.getGuests() <= 0) {
            throw new BusinessException(ResponseCode.GUESTS_INVALID);
        }

        var checkinDate = LocalDate.parse(request.getCheckinDate());
        var checkoutDate = LocalDate.parse(request.getCheckoutDate());
        if (checkinDate.isAfter(checkoutDate)) {
            throw new BusinessException(ResponseCode.CHECKIN_DATE_INVALID);
        }

        if (checkinDate.isBefore(LocalDate.now())) {
            throw new BusinessException(ResponseCode.CHECKIN_DATE_INVALID);
        }

        int nights = (int) DateUtil.getDiffInDays(checkinDate, checkoutDate);
        if (nights > NIGHT_MAX) {
            throw new BusinessException(ResponseCode.NIGHTS_INVALID);
        }

        log.debug("[request_id={}] User user_id={} is locking homestay_id={} for {} nights, checkin_date={} and checkout_date={}", request.getUserId(), request.getHomestayId());
        var aDays = availabilityRepository.findAndLockHomestayAvailability(
                request.getHomestayId(),
                AvailabilityStatus.AVAILABLE.getValue(),
                request.getCheckinDate(),
                checkoutDate.minusDays(1).toString()
        );
        log.debug("[request_id={}] User user_id={} locked homestay_id={} for {} nights, checkin_date={} and checkout_date={}", request.getUserId(), request.getHomestayId(), nights, request.getCheckinDate(), checkoutDate.minusDays(1).toString());
        if (aDays.isEmpty() || aDays.size() < nights) {
            throw new BusinessException(ResponseCode.HOMESTAY_BUSY);
        }

        var subtotal = BigDecimal.ZERO;
        for (var aDay: aDays) {
            aDay.setStatus(AvailabilityStatus.BOOKED.getValue());
            subtotal = subtotal.add(aDay.getPrice());
        }

        var discount = discountService.getDiscountAmount(subtotal, nights);
        var totalAmount = subtotal.add(discount);

        var booking = Booking.builder()
                .homestayId(request.getHomestayId())
                .userId(request.getUserId())
                .checkinDate(checkinDate)
                .checkoutDate(checkoutDate)
                .guests(request.getGuests())
                .subtotal(subtotal)
                .discount(discount)
                .totalAmount(totalAmount)
                .currency(Currency.USD.getValue())
                .note(request.getNote())
                .status(BookingStatus.BOOKED.getValue())
                .requestId(request.getRequestId())
                .build();

        availabilityRepository.saveAll(aDays);
        bookingRepository.save(booking);
        log.info("[request_id={}] User user_id={} created booking_id={}", request.getRequestId(), request.getUserId(), booking.getId());
        return mapper.toResponse(booking);
    }
}
