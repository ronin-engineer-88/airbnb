package com.roninhub.airbnb.domain.homestay.repository;

import com.roninhub.airbnb.domain.homestay.dto.HomestayDTO;
import com.roninhub.airbnb.domain.homestay.entity.Homestay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface HomestayRepository extends JpaRepository<Homestay, Long> {

    @Query(value = """
    with destination as (
        select st_transform(st_setsrid(st_makepoint(:longitude, :latitude), 4326), 3857) as geom
    )
    select id, name, description, images, address, bedrooms, vh.night_amount, vh.total_amount
    from destination d, homestay hs inner join
    (
        select h.id, avg(ha.price) as night_amount, sum(ha.price) as total_amount
         from destination d,
              homestay h
         join homestay_availability ha on h.id = ha.homestay_id
         where st_dwithin(h.geom, d.geom, :radius)
           and h.guests >= :guests
           and ha.date between :checkinDate and :checkoutDate
           and ha.status = :status
         group by h.id
         having count(ha.date) = :nights
    ) as vh using (id)
    order by hs.geom <-> d.geom
    """, nativeQuery = true)
    List<HomestayDTO> searchHomestay(@Param("longitude") Double longitude,
                                     @Param("latitude") Double latitude,
                                     @Param("radius") Double radius,
                                     @Param("checkinDate") LocalDate checkinDate,
                                     @Param("checkoutDate") LocalDate checkoutDate,
                                     @Param("nights") Integer nights,
                                     @Param("guests") Integer guests,
                                     @Param("status") Integer status);

    //    @Query(value = """
//    SELECT new com.roninhub.airbnb.domain.homestay.dto.response.HomestayDetail(
//    h.id, h.name, h.description, vh.avg_price)
//    FROM Homestay h INNER JOIN (
//        SELECT h.id, AVG(ha.price) as avg_price
//        FROM Homestay h
//        JOIN HomestayAvailability ha ON h.id = ha.homestayId
//        WHERE h.status = :status
//        AND ha.date BETWEEN cast(:checkInDate as date) AND cast(:checkOutDate as date)
//        GROUP BY h.id
//        HAVING COUNT(ha.date) = (cast(:checkOutDate as date) - cast(:checkInDate as date)) + 1
//    ) AS vh ON h.id = vh.id
//    """)
//
//    List<HomestayDetail> searchHomestay(@Param("status") Integer status,
//                                        @Param("checkInDate") String checkInDate,
//                                        @Param("checkOutDate") String checkOutDate);

}
