package com.roninhub.airbnb.api;

import com.roninhub.airbnb.domain.payment.dto.response.VNPayIpnResponse;
import com.roninhub.airbnb.domain.payment.service.IpnHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/payments")
@Slf4j
@RequiredArgsConstructor
public class PaymentController {

    private final IpnHandler ipnHandler;


    @GetMapping("/vnpay_ipn")
    VNPayIpnResponse processIpn(@RequestParam Map<String, String> params) {
        log.info("VNPay IPN: {}", params);
        return ipnHandler.process(params);
    }
}
