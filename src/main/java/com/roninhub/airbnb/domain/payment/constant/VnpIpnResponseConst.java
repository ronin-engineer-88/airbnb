package com.roninhub.airbnb.domain.payment.constant;

import com.roninhub.airbnb.domain.payment.dto.response.VNPayIpnResponse;

public class VnpIpnResponseConst {

    public static final VNPayIpnResponse SUCCESS = new VNPayIpnResponse("00", "Successful");
    public static final VNPayIpnResponse SIGNATURE_FAILED = new VNPayIpnResponse("97", "Signature failed");
    public static final VNPayIpnResponse ORDER_NOT_FOUND = new VNPayIpnResponse("01", "Order not found");
    public static final VNPayIpnResponse UNKNOWN_ERROR = new VNPayIpnResponse("99", "Unknown error");
}
