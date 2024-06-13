package com.roninhub.airbnb.domain.user.service;


import com.roninhub.airbnb.domain.user.entity.Profile;
import com.roninhub.airbnb.domain.user.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository repository;


    public Profile getProfileByUserId(Long userId) {
        return repository.findById(userId).orElse(null);
    }

}
