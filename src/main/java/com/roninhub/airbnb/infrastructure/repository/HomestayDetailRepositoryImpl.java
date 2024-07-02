//package com.roninhub.airbnb.infrastructure.repository;
//
//import com.roninhub.airbnb.domain.homestay.dto.HomestayDTO;
//import com.roninhub.airbnb.domain.homestay.dto.request.HomestaySearchRequest;
//import com.roninhub.airbnb.domain.homestay.dto.response.HomestayDetail;
//import com.roninhub.airbnb.domain.homestay.repository.HomestayDetailRepository;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.Query;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@Repository
//@RequiredArgsConstructor
//public class HomestayDetailRepositoryImpl implements HomestayDetailRepository {
//
//    private final static String SEARCH_HOMESTAY_BASE_SQL = """
//            select id, name, description, type, status, phone_number,
//            address, guests, bedrooms, bathrooms, version, avg_price
//            from homestay inner join (
//            select h.id, avg(ha.price) as avg_price
//            from homestay h
//            join homestay_availability ha on h.id = ha.homestay_id
//            where ha.status = :status
//            and h.guests >= :guests
//            and ha.date between cast(:checkInDate as date) and cast(:checkOutDate as date)
//            %s
//            group by h.id
//            having count(ha.date) = (cast(:checkOutDate as date) - cast(:checkInDate as date)) + 1)
//            as vh using (id)
//            """;
//
//    private final EntityManager entityManager;
//
//
//    @Override
//    public List<HomestayDetail> searchHomestays(HomestaySearchRequest request) {
//        var sql = buildSearchHomestaySQL(request);
//        var query = entityManager.createNativeQuery(sql, "HomestayDetailMapping");
////        var query = entityManager.createNativeQuery(sql, HomestayDTO.class);
//        setParameters(query, request);
//
//        return query.getResultList();
//    }
//
//    private String buildSearchHomestaySQL(HomestaySearchRequest request) {
//        StringBuilder addressBuilder = new StringBuilder();
//
//        if (request.getWardId() != null) {
//            addressBuilder.append("and h.ward_id = :wardId ");
//        }
//        if (request.getDistrictId() != null) {
//            addressBuilder.append("and h.district_id = :districtId ");
//        }
//        if (request.getCityId() != null) {
//            addressBuilder.append("and h.city_id = :cityId ");
//        }
//        if (request.getStateId() != null) {
//            addressBuilder.append("and h.province_id = :provinceId ");
//        }
//
//        return String.format(SEARCH_HOMESTAY_BASE_SQL, addressBuilder.toString());
//    }
//
//
//    private void setParameters(Query query, HomestaySearchRequest request) {
//        query.setParameter("status", request.getStatus());
//        query.setParameter("guests", request.getGuests());
//        query.setParameter("checkInDate", request.getCheckinDate());
//        query.setParameter("checkOutDate", request.getCheckoutDate());
//
//        if (request.getWardId() != null) {
//            query.setParameter("wardId", request.getWardId());
//        }
//        if (request.getDistrictId() != null) {
//            query.setParameter("districtId", request.getDistrictId());
//        }
//        if (request.getCityId() != null) {
//            query.setParameter("cityId", request.getCityId());
//        }
//        if (request.getStateId() != null) {
//            query.setParameter("provinceId", request.getStateId());
//        }
//    }
//}
