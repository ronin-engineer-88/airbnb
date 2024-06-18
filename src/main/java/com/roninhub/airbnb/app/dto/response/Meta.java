package com.roninhub.airbnb.app.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Meta {

    @JsonProperty("request_id")
    private String requestId;

    private String type;

    private String message;

    @JsonProperty("service_id")
    private String serviceId;

    @JsonProperty("extra_meta")
    private Map<String, Object> extraMeta;

}
