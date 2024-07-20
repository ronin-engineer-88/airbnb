package com.roninhub.airbnb.domain.payment.service;

import com.roninhub.airbnb.domain.payment.dto.InitPaymentRequest;
import com.roninhub.airbnb.domain.payment.dto.InitPaymentResponse;

public interface PaymentService {

    InitPaymentResponse init(InitPaymentRequest request);
}
