package com.roninhub.airbnb.domain.payment.service;


import com.roninhub.airbnb.domain.booking.service.BookingService;
import com.roninhub.airbnb.domain.common.exception.BusinessException;
import com.roninhub.airbnb.domain.payment.constant.VNPayParams;
import com.roninhub.airbnb.domain.payment.constant.VnpIpnResponseConst;
import com.roninhub.airbnb.domain.payment.dto.response.VNPayIpnResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class IpnHandler {

    private final VNPayService vnPayService;

    private final BookingService bookingService;


    public VNPayIpnResponse process(Map<String, String> params) {
        if (!vnPayService.verifyIpn(params)) {
            return VnpIpnResponseConst.SIGNATURE_FAILED;
        }

        VNPayIpnResponse response;
        try {
            var bookingId = Long.parseLong(params.get(VNPayParams.TXN_REF));
            bookingService.markBooked(bookingId);
            response = VnpIpnResponseConst.SUCCESS;
        }
        catch (BusinessException e) {
            switch (e.getResponseCode()) {
                case BOOKING_NOT_FOUND -> response = VnpIpnResponseConst.ORDER_NOT_FOUND;
                default -> response = VnpIpnResponseConst.UNKNOWN_ERROR;
            }
        }
        catch (Exception e) {
            response = VnpIpnResponseConst.UNKNOWN_ERROR;
        }

        log.info("VNPay Ipn Response: {}", response);
        return response;
    }
}
