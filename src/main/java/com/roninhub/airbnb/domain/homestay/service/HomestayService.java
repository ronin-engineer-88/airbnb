package com.roninhub.airbnb.domain.homestay.service;

import com.roninhub.airbnb.domain.homestay.entity.Homestay;
import com.roninhub.airbnb.domain.homestay.entity.HomestayDetail;
import com.roninhub.airbnb.domain.homestay.repository.HomestayDetailRepository;
import com.roninhub.airbnb.domain.homestay.repository.HomestayRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
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
        Homestay homestay = repository.findById(id).orElse(null);
        return homestay;
    }

    @PostConstruct
    public void init() {
        var homestayDetails = detailRepository.searchHomestay(0, "2024-06-12", "2024-06-15");
        log.debug("hotels: {}", homestayDetails);
    }
}
