package com.roninhub.airbnb.domain.homestay.service;

import com.roninhub.airbnb.domain.booking.constant.AvailabilityStatus;
import com.roninhub.airbnb.domain.homestay.dto.request.HomestaySearchRequest;
import com.roninhub.airbnb.domain.homestay.entity.Homestay;
import com.roninhub.airbnb.domain.homestay.dto.response.HomestayDetail;
import com.roninhub.airbnb.domain.homestay.repository.HomestayDetailRepository;
import com.roninhub.airbnb.domain.homestay.repository.HomestayRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class HomestayService {

    private final HomestayRepository repository;
    private final HomestayDetailRepository detailRepository;


    public Homestay getHomestayById(Long id) {
        var homestay = repository.findById(id).orElse(null);
        return homestay;
    }

    public List<HomestayDetail> searchHomestays(HomestaySearchRequest request) {
        request.setStatus(AvailabilityStatus.AVAILABLE);
        var homestayDetails = detailRepository.searchHomestays(request);

        return homestayDetails;
    }
}
