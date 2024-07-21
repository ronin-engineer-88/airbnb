package com.roninhub.airbnb.domain.booking.service;

import com.roninhub.airbnb.domain.booking.constant.AvailabilityStatus;
import com.roninhub.airbnb.domain.booking.constant.BookingStatus;
import com.roninhub.airbnb.domain.booking.dto.request.BookingRequest;
import com.roninhub.airbnb.domain.booking.dto.response.BookingDto;
import com.roninhub.airbnb.domain.booking.dto.response.BookingResponse;
import com.roninhub.airbnb.domain.booking.dto.response.BookingStatusResponse;
import com.roninhub.airbnb.domain.booking.entity.Booking;
import com.roninhub.airbnb.domain.booking.mapper.BookingMapper;
import com.roninhub.airbnb.domain.booking.repository.BookingRepository;
import com.roninhub.airbnb.domain.common.constant.ResponseCode;
import com.roninhub.airbnb.domain.common.exception.BusinessException;
import com.roninhub.airbnb.domain.homestay.constant.HomestayStatus;
import com.roninhub.airbnb.domain.homestay.service.HomestayService;
import com.roninhub.airbnb.domain.payment.dto.request.InitPaymentRequest;
import com.roninhub.airbnb.domain.payment.service.PaymentService;
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

    private final BookingRepository repository;
    private final AvailabilityService availabilityService;
    private final HomestayService homestayService;
    private final PricingService pricingService;
    private final PaymentService paymentService;
    private final BookingMapper mapper;


    @SneakyThrows
    @Transactional
    public BookingResponse book(final BookingRequest request) {
        validateRequest(request);
        validateHomestay(request);

        final var homestayId = request.getHomestayId();
        final var checkinDate = request.getCheckinDate();
        final var checkoutDate = request.getCheckoutDate();

        log.debug("[request_id={}] User user_id={} is acquiring lock homestay_id={} from checkin_date={} to checkout_date={}", request.getRequestId(), request.getUserId(), homestayId, checkinDate, checkoutDate);
        final var aDays = availabilityService.checkAvailabilityForBooking(homestayId, checkinDate, checkoutDate);
        log.debug("[request_id={}] User user_id={} locked homestay_id={} from checkin_date={} to checkout_date={}", request.getRequestId(), request.getUserId(), request.getHomestayId(), checkinDate, checkoutDate);

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
                .status(BookingStatus.PAYMENT_PROCESSING.getValue())
                .requestId(request.getRequestId())
                .build();

        aDays.forEach(a -> a.setStatus(AvailabilityStatus.HELD.getValue()));

        availabilityService.saveAll(aDays);
        repository.save(booking);

        var initPaymentRequest = InitPaymentRequest.builder()
                .userId(booking.getUserId())
                .amount(booking.getTotalAmount().longValue())
                .txnRef(String.valueOf(booking.getId()))
                .requestId(booking.getRequestId())
                .ipAddress(request.getIpAddress())
                .build();

        var initPaymentResponse = paymentService.init(initPaymentRequest);
        var bookingDto = mapper.toBookingDto(booking);
        log.info("[request_id={}] User user_id={} created booking_id={} successfully", request.getRequestId(), request.getUserId(), booking.getId());
        return BookingResponse.builder()
                .booking(bookingDto)
                .payment(initPaymentResponse)
                .build();
    }

    @Transactional
    public BookingDto markBooked(Long bookingId) {
        final var bookingOpt = repository.findById(bookingId);
        if (bookingOpt.isEmpty()) {
            throw new BusinessException(ResponseCode.BOOKING_NOT_FOUND);
        }

        final var booking = bookingOpt.get();
        final var aDays = availabilityService.getRange(
                booking.getHomestayId(),
                booking.getCheckinDate(),
                booking.getCheckoutDate()
        );

        booking.setStatus(BookingStatus.BOOKED.getValue());
        aDays.forEach(a -> a.setStatus(AvailabilityStatus.BOOKED.getValue()));

        availabilityService.saveAll(aDays);
        repository.save(booking);

        return mapper.toBookingDto(booking);
    }

    public BookingStatusResponse getBookingStatus(Long bookingId) {
        final var bookingOpt = repository.findById(bookingId);
        if (bookingOpt.isEmpty()) {
            throw new BusinessException(ResponseCode.BOOKING_NOT_FOUND);
        }

        return mapper.toBookingStatusResponse(bookingOpt.get());
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
