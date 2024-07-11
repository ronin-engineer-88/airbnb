package com.roninhub.airbnb.domain.booking.service;

import com.roninhub.airbnb.domain.booking.constant.AvailabilityStatus;
import com.roninhub.airbnb.domain.booking.entity.HomestayAvailability;
import com.roninhub.airbnb.domain.booking.repository.HomestayAvailabilityRepository;
import com.roninhub.airbnb.domain.common.constant.ResponseCode;
import com.roninhub.airbnb.domain.common.exception.BusinessException;
import com.roninhub.airbnb.infrastructure.util.DateUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AvailabilityService {
    private static final int NIGHT_MAX = 365;

    private final HomestayAvailabilityRepository availabilityRepository;

    public List<HomestayAvailability> checkAvailabilityForBooking(final Long homestayId,
                                                                  final LocalDate checkinDate,
                                                                  final LocalDate checkoutDate) {

        final int nights = (int) DateUtil.getDiffInDays(checkinDate, checkoutDate);
        if (nights > NIGHT_MAX) {
            throw new BusinessException(ResponseCode.NIGHTS_INVALID);
        }

        final var aDays = availabilityRepository.findAndLockHomestayAvailability(
                homestayId,
                AvailabilityStatus.AVAILABLE.getValue(),
                checkinDate,
                checkoutDate.minusDays(1)
        );
        if (aDays.isEmpty() || aDays.size() < nights) {
            throw new BusinessException(ResponseCode.HOMESTAY_BUSY);
        }

        return aDays;
    }


    @Transactional
    public void saveAll(List<HomestayAvailability> aDays) {
        availabilityRepository.saveAll(aDays);
    }
}
