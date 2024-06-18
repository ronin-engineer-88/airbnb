package com.roninhub.airbnb.domain.booking.repository;

import com.roninhub.airbnb.domain.booking.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface BookingRepository extends JpaRepository<Booking, Long> {
}
