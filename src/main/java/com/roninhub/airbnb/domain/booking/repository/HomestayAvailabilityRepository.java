package com.roninhub.airbnb.domain.booking.repository;

import com.roninhub.airbnb.domain.booking.entity.HomestayAvailability;
import com.roninhub.airbnb.domain.booking.entity.HomestayAvailabilityId;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;


public interface HomestayAvailabilityRepository extends JpaRepository<HomestayAvailability, HomestayAvailabilityId> {

    @Query(value = """
        SELECT new HomestayAvailability(ha.homestayId, ha.date, ha.price, ha.status)
        FROM HomestayAvailability ha
        WHERE ha.homestayId = :homestayId
        AND ha.status = :status
        AND ha.date BETWEEN :checkinDate AND :checkoutDate
        """)
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<HomestayAvailability> findAndLockHomestayAvailability(@Param("homestayId") Long homestayId,
                                                               @Param("status") Integer status,
                                                               @Param("checkinDate") LocalDate checkinDate,
                                                               @Param("checkoutDate") LocalDate checkoutDate);
}
