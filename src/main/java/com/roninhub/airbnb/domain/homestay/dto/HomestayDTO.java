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

    String getAddress();

    Integer getGuests();

    Integer getBedrooms();

    Long getVersion();

    Double getNightAmount();

    Double getTotalAmount();
}
