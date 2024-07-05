package com.roninhub.airbnb.domain.homestay.dto;

import java.util.List;

public interface HomestayDTO {

    Long getId();

    String getName();

    String getDescription();

    List<String> getImages();

    Integer getType();

    Integer getStatus();

    String getPhoneNumber();

    Integer getGuests();

    Integer getBedrooms();

    Long getVersion();

    Double getNightAmount();

    Double getTotalAmount();

    String getAddress();

    Double getLongitude();

    Double getLatitude();
}
