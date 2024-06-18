package com.roninhub.airbnb.domain.homestay.repository;

import com.roninhub.airbnb.domain.homestay.dto.request.HomestaySearchRequest;
import com.roninhub.airbnb.domain.homestay.dto.response.HomestayDetail;

import java.util.List;

public interface HomestayDetailRepository {


    List<HomestayDetail> searchHomestays(HomestaySearchRequest request);

}
