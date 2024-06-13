package com.roninhub.airbnb.domain.homestay.repository;

import com.roninhub.airbnb.domain.homestay.entity.HomestayDetail;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HomestayDetailRepository {


    List<HomestayDetail> searchHomestay(Integer status,
                                        String startDate,
                                        String endDate);

}
