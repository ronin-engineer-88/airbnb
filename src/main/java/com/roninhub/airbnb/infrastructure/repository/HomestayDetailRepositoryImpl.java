package com.roninhub.airbnb.infrastructure.repository;

import com.roninhub.airbnb.domain.homestay.entity.HomestayDetail;
import com.roninhub.airbnb.domain.homestay.repository.HomestayDetailRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TemporalType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class HomestayDetailRepositoryImpl implements HomestayDetailRepository {

    private final static String SEARCH_HOMESTAY_SQL = """
            select * from homestay inner join (
            select h.id, avg(ha.price) as avg_price
            from homestay h
            join homestay_availability ha on h.id = ha.homestay_id
            where ha.status = :status
            and ha.date between cast(:checkInDate as date) and cast(:checkOutDate as date)
            group by h.id
            having count(ha.date) = (cast(:checkOutDate as date) - cast(:checkInDate as date)) + 1)
            as vh using (id)
            """;

    private final EntityManager entityManager;

    @Override
    public List<HomestayDetail> searchHomestay(Integer status, String checkInDate, String checkOutDate) {
        Query query = entityManager.createNativeQuery(SEARCH_HOMESTAY_SQL, "HomestayDetailMapping");

        query.setParameter("status", status);
        query.setParameter("checkInDate", checkInDate);
        query.setParameter("checkOutDate", checkOutDate);

        return query.getResultList();
    }
}
