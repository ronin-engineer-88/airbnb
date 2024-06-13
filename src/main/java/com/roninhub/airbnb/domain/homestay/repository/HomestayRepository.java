package com.roninhub.airbnb.domain.homestay.repository;

import com.roninhub.airbnb.domain.homestay.entity.Homestay;
import com.roninhub.airbnb.domain.homestay.entity.HomestayDetail;
import com.roninhub.airbnb.domain.booking.entity.HomestayAvailability;
import jakarta.persistence.SqlResultSetMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HomestayRepository extends JpaRepository<Homestay, Long> {


    //
//    @Query(value = """
//    SELECT new com.roninhub.airbnb.domain.homestay.entity.HomestayDetail(
//    h.id, h.name, h.description, vh.avg_price)
//    FROM Homestay h INNER JOIN (
//        SELECT h.id, AVG(ha.price) as avg_price
//        FROM Homestay h
//        JOIN HomestayAvailability ha ON h.id = ha.homestayId
//        WHERE h.status = :status
//        AND ha.date BETWEEN :startDate AND :endDate
//        GROUP BY h.id
//        HAVING COUNT(ha.date) = (date(:endDate) - date(:startDate)) + 1
//    ) AS vh ON h.id = vh.id
//    """)
//    List<HomestayDetail> searchHomestay(@Param("status") Integer status,
//                                        @Param("startDate") String startDate,
//                                        @Param("endDate") String endDate);


//    @Query(value = """
//            select * from homestay inner join (
//                select h.id, avg(ha.price) as avg_price
//                from homestay h
//                join homestay_availability ha on h.id = ha.homestay_id
//                where ha.status = :status
//                and ha.date between :checkInDate and :checkOutDate
//                group by h.id
//                having count(ha.date) = (date :checkOutDate - date :checkInDate) + 1)
//            as vh using (id)
//            """, nativeQuery = true)
//    List<HomestayDetail> searchHomestay(@Param("status") Integer status,
//                                        @Param("checkInDate") String checkInDate,
//                                        @Param("checkOutDate") String checkOutDate);
}
