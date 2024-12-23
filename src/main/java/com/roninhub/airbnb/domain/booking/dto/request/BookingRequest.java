package com.roninhub.airbnb.domain.booking.dto.request;


import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class BookingRequest {

    @NotBlank(message = "message cannot be blank")
    private String requestId;

    @NotNull(message = "user_id cannot be null")
    private Long userId;

    @NotNull(message = "homestay_id cannot be blank")
    private Long homestayId;

    @NotNull(message = "checkin_date cannot be blank")
//    @Pattern(
//            regexp = DateConst.ISO_8601_PATTERN,
//            message = "checkin_date is invalid"
//    )
    private LocalDate checkinDate;

    @NotNull(message = "checkout_date cannot be blank")
//    @Pattern(
//            regexp = DateConst.ISO_8601_PATTERN,
//            message = "checkout_date is invalid"
//    )
    private LocalDate checkoutDate;

    @Positive(message = "guests must be positive")
    private Integer guests;

    @Length(max = 500, message = "note cannot be longer than 255 characters")
    private String note;
}
