package com.roninhub.airbnb.app.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Map;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Meta {

    @JsonProperty("request_id")
    private String requestId;

    private String status;

    private String message;

    @JsonProperty("service_id")
    private String serviceId;

    private Collection<ApiError> errors;

    @JsonProperty("extra_meta")
    private Map<String, Object> extraMeta;

}
