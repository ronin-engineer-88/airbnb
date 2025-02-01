package com.roninhub.airbnb.domain.booking.service.booking_v2;

import com.roninhub.airbnb.domain.booking.constant.AvailabilityStatus;
import com.roninhub.airbnb.domain.booking.constant.BookingStatus;
import com.roninhub.airbnb.domain.booking.dto.BookingPrice;
import com.roninhub.airbnb.domain.booking.dto.request.BookingRequest;
import com.roninhub.airbnb.domain.booking.dto.response.BookingResponse;
import com.roninhub.airbnb.domain.booking.entity.Booking;
import com.roninhub.airbnb.domain.booking.entity.HomestayAvailability;
import com.roninhub.airbnb.domain.booking.mapper.BookingMapper;
import com.roninhub.airbnb.domain.booking.repository.BookingRepository;
import com.roninhub.airbnb.domain.booking.repository.HomestayAvailabilityRepository;
import com.roninhub.airbnb.domain.booking.service.PricingService;
import com.roninhub.airbnb.domain.common.constant.ResponseCode;
import com.roninhub.airbnb.domain.common.exception.BusinessException;
import com.roninhub.airbnb.domain.homestay.constant.HomestayStatus;
import com.roninhub.airbnb.domain.homestay.entity.Homestay;
import com.roninhub.airbnb.domain.homestay.service.HomestayService;
import com.roninhub.airbnb.infrastructure.util.DateUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BaseBookingService implements BookingService {

    private static final int NIGHT_MAX = 365;

    private BookingRepository bookingRepository;
    private HomestayAvailabilityRepository availabilityRepository;
    private HomestayService homestayService;
    private PricingService pricingService;
    private BookingMapper mapper;

    @SneakyThrows
    @Transactional
    // Template Method
    public BookingResponse execute(BookingRequest request) {
        validateRequest(request);
        var homestay = validateHomestay(request);
        var aDays = checkAvailability(homestay, request);

        var price = pricingService.calculate(homestay, aDays);
        var booking = buildBooking(request, price);
        aDays.forEach(a -> a.setStatus(AvailabilityStatus.BOOKED.getValue()));

        availabilityRepository.saveAll(aDays);
        bookingRepository.save(booking);

        sendNotifications(booking);
        postProcess(booking);   // hook method

        log.info("[request_id={}] User user_id={} created booking_id={} successfully", request.getRequestId(), request.getUserId(), booking.getId());
        return mapper.toResponse(booking);
    }

    protected void validateRequest(final BookingRequest request) {
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

    protected Homestay validateHomestay(final BookingRequest request) {
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

        return homestay;
    }

    protected List<HomestayAvailability> checkAvailability(Homestay homestay, BookingRequest request) {
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

        return aDays;
    }

    protected Booking buildBooking(BookingRequest request, BookingPrice price) {
        final LocalDate checkinDate = request.getCheckinDate();
        final LocalDate checkoutDate = request.getCheckoutDate();

        return Booking.builder()
                .homestayId(request.getHomestayId())
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
    }

    protected void sendNotifications(Booking booking) {
        log.info("Sending email to user={}", booking.getUserId());
    }

    protected void postProcess(Booking booking) {
    }
}
