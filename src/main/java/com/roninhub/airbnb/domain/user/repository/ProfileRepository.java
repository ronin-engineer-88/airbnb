package com.roninhub.airbnb.domain.user.repository;


import com.roninhub.airbnb.domain.user.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
}
