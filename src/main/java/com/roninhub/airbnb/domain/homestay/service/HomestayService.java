package com.roninhub.airbnb.domain.homestay.service;

import com.roninhub.airbnb.domain.booking.constant.AvailabilityStatus;
import com.roninhub.airbnb.domain.common.constant.ResponseCode;
import com.roninhub.airbnb.domain.common.exception.BusinessException;
import com.roninhub.airbnb.domain.homestay.dto.HomestayDTO;
import com.roninhub.airbnb.domain.homestay.dto.request.HomestaySearchRequest;
import com.roninhub.airbnb.domain.homestay.entity.Homestay;
import com.roninhub.airbnb.domain.homestay.dto.response.HomestayDetail;
import com.roninhub.airbnb.domain.homestay.repository.HomestayDetailRepository;
import com.roninhub.airbnb.domain.homestay.repository.HomestayRepository;
import com.roninhub.airbnb.infrastructure.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class HomestayService {

    private final HomestayRepository repository;


    public Homestay getHomestayById(Long id) {
        var homestay = repository.findById(id).orElse(null);
        return homestay;
    }

    public List<HomestayDTO> searchHomestays(HomestaySearchRequest request) {
        request.setStatus(AvailabilityStatus.AVAILABLE);

        var checkinDate = request.getCheckinDate();
        var checkoutDate = request.getCheckoutDate();

//        if (request.getCheckinDate().isAfter(request.getCheckoutDate())) {
//            throw new BusinessException(ResponseCode.CHECKIN_DATE_INVALID);
//        }
//
//        if (request.getCheckinDate().isBefore(LocalDate.now())) {
//            throw new BusinessException(ResponseCode.CHECKIN_DATE_INVALID);
//        }


        int nights = (int) DateUtil.getDiffInDays(checkinDate, checkoutDate);
        checkoutDate = checkoutDate.minusDays(1);

        var homestays = repository.searchHomestay(
                request.getLongitude(),
                request.getLatitude(),
                request.getRadius(),
                checkinDate,
                checkoutDate,
                nights,
                request.getGuests(),
                request.getStatus().getValue()
        );

        return homestays;
    }
}
