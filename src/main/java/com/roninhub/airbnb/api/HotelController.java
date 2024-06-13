package com.roninhub.airbnb.api;

import com.roninhub.airbnb.domain.homestay.entity.Homestay;
import com.roninhub.airbnb.domain.homestay.service.HomestayService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

//    public
}
