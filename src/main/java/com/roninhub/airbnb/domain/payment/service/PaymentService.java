package com.roninhub.airbnb.domain.payment.service;

import com.roninhub.airbnb.domain.payment.dto.request.InitPaymentRequest;
import com.roninhub.airbnb.domain.payment.dto.response.InitPaymentResponse;

public interface PaymentService {

    InitPaymentResponse init(InitPaymentRequest request);
}
