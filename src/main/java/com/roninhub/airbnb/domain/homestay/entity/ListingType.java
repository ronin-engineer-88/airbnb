package com.roninhub.airbnb.domain.homestay.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@AllArgsConstructor
@Getter
public enum ListingType {
    HOUSE(0),
    APARTMENT(1),
    CONDO(2),
    VILLA(3),
    OTHER(-1)   // DEFAULT
    ;

    final int value;
    private static final Map<Integer, ListingType> VALUE_MAP = Stream.of(values())
            .collect(Collectors.toMap(ListingType::getValue, type -> type));

    public static ListingType of(int value) {
        return VALUE_MAP.getOrDefault(value, HOUSE);
    }

    public int maxGuests() {
        return switch (this) {
            case APARTMENT -> 4;
            case HOUSE -> 10;
            default -> 8;
        };
    }
}
