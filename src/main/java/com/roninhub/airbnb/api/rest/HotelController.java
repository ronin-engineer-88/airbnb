package com.roninhub.airbnb.api.rest;

import com.roninhub.airbnb.app.dto.response.ResponseDto;
import com.roninhub.airbnb.app.service.ResponseFactory;
import com.roninhub.airbnb.domain.homestay.dto.request.HomestaySearchRequest;
import com.roninhub.airbnb.domain.homestay.entity.Homestay;
import com.roninhub.airbnb.domain.homestay.service.HomestayService;
import com.roninhub.airbnb.infrastructure.util.DateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/homestays")
@RequiredArgsConstructor
public class HotelController {

    private final HomestayService service;
    private final ResponseFactory responseFactory;


    @GetMapping("/{id}")
    public Homestay getHomestayById(@PathVariable Long id) {
        return service.getHomestayById(id);
    }


    @GetMapping
    public ResponseDto searchHomestay(@RequestParam(value = "longitude") Double longitude,
                                      @RequestParam(value = "latitude") Double latitude,
                                      @RequestParam(value = "radius") Double radius,
                                      @RequestParam(value = "checkin_date") String checkinDate,
                                      @RequestParam(value = "checkout_date") String checkoutDate,
                                      @RequestParam(value = "guests") Integer guests) {

        var request = HomestaySearchRequest.builder()
                .longitude(longitude)
                .latitude(latitude)
                .radius(radius)
                .checkinDate(DateUtil.parse(checkinDate))
                .checkoutDate(DateUtil.parse(checkoutDate))
                .guests(guests)
                .build();

        var result = service.searchHomestays(request);

        return responseFactory.response(result);
    }
}
