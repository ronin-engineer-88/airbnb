package com.roninhub.airbnb.api;

import com.roninhub.airbnb.domain.homestay.dto.request.HomestaySearchRequest;
import com.roninhub.airbnb.domain.homestay.entity.Homestay;
import com.roninhub.airbnb.domain.homestay.dto.response.HomestayDetail;
import com.roninhub.airbnb.domain.homestay.service.HomestayService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/homestays")
@RequiredArgsConstructor
public class HotelController {

    private final HomestayService service;


    @GetMapping("/{id}")
    public Homestay getHomestayById(@PathVariable Long id) {
        return service.getHomestayById(id);
    }

    @GetMapping
    public List<HomestayDetail> searchHomestay(@RequestParam("checkin_date") String checkInDate,
                                               @RequestParam("checkout_date") String checkOutDate,
                                               @RequestParam("guests") Integer guests,
                                               @RequestParam(value = "ward_id", required = false) Integer wardId,
                                               @RequestParam(value = "district_id", required = false) Integer districtId,
                                               @RequestParam(value = "city_id", required = false) Integer cityId,
                                               @RequestParam(value = "state_id", required = false) Integer stateId) {
        var request = HomestaySearchRequest.builder()
                .checkInDate(checkInDate)
                .checkOutDate(checkOutDate)
                .guests(guests)
                .wardId(wardId)
                .districtId(districtId)
                .cityId(cityId)
                .stateId(stateId)
                .build();

        return service.searchHomestays(request);
    }
}
