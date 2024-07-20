package com.roninhub.airbnb.domain.payment.dto.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VNPayInitPaymentRequest {

    @JsonProperty("vnp_Version")
    public static final String VERSION = "2.1.0";

    @JsonProperty("vnp_Command")
    public static final String COMMAND = "querydr";

    @JsonProperty("vnp_RequestId")
    private String requestId;

    @JsonProperty("vnp_TmnCode")
    private String tmnCode;

    @JsonProperty("vnp_TxnRef")
    private String txnRef;

    @JsonProperty("vnp_CreateDate")
    private String createdDate;

    @JsonProperty("vnp_IpAddr")
    private String ipAddress;

    @JsonProperty("vnp_OrderInfo")
    private String orderInfo;

    @JsonProperty("vnp_SecureHash")
    private String secureHash;
}
