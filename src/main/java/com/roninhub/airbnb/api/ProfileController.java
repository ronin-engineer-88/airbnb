package com.roninhub.airbnb.api;


import com.roninhub.airbnb.domain.user.entity.Profile;
import com.roninhub.airbnb.domain.user.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/profiles")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService service;


    @GetMapping("/{id}")
    public Profile getProfileById(@PathVariable Long id) {
        return service.getProfileByUserId(id);
    }
}
