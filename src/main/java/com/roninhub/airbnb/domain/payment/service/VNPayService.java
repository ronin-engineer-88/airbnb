package com.roninhub.airbnb.domain.payment.service;

import com.roninhub.airbnb.domain.common.constant.Currency;
import com.roninhub.airbnb.domain.common.constant.Locale;
import com.roninhub.airbnb.domain.payment.dto.InitPaymentRequest;
import com.roninhub.airbnb.domain.payment.dto.InitPaymentResponse;
import com.roninhub.airbnb.infrastructure.constant.Symbol;
import com.roninhub.airbnb.infrastructure.util.DateUtil;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;


@Service
@Slf4j
@RequiredArgsConstructor
public class VNPayService implements PaymentService {

    public static final String VERSION = "2.1.0";
    public static final String COMMAND = "pay";
    public static final String ORDER_TYPE = "190000";
    public static final long DEFAULT_MULTIPLIER = 100L;

    @Value("${payment.vnpay.tmn-code}")
    private String tmnCode;

    @Value("${payment.vnpay.init-payment-url}")
    private String initPaymentPrefixUrl;

    @Value("${payment.vnpay.return-url}")
    private String returnUrlFormat;

    @Value("${payment.vnpay.timeout}")
    private Integer paymentTimeout;

    private final CryptoService cryptoService;


    @PostConstruct
    void init() {
        var resp = init(InitPaymentRequest.builder()
                .requestId("r45")
                .ipAddress("127.0.0.1")
                .userId(34L)
                .txnRef("b02")
                .amount(100000)
                .build());
        log.info("Init payment url: {}", resp.getVnpUrl());
    }

    public InitPaymentResponse init(InitPaymentRequest request) {
        var requestId = request.getRequestId();
        var txnRef = request.getTxnRef();
        var ipAddress = request.getIpAddress();
        var orderInfo = buildPaymentDetail(request);
        var amount = request.getAmount() * DEFAULT_MULTIPLIER;
        var returnUrl = buildReturnUrl(txnRef);
        var vnCalendar = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        var createdDate = DateUtil.formatVnTime(vnCalendar);
        vnCalendar.add(Calendar.MINUTE, paymentTimeout);
        var expiredDate = DateUtil.formatVnTime(vnCalendar);

        Map<String, String> params = new HashMap<>();

        params.put("vnp_Version", VERSION);
        params.put("vnp_Command", COMMAND);

        params.put("vnp_TmnCode", tmnCode);
        params.put("vnp_Amount", String.valueOf(amount));
        params.put("vnp_CurrCode", Currency.VND.getValue());
        params.put("vnp_TxnRef", txnRef);

        params.put("vnp_ReturnUrl", returnUrl);

        params.put("vnp_CreateDate", createdDate);
        params.put("vnp_ExpireDate", expiredDate);
        params.put("vnp_IpAddr", ipAddress);
        params.put("vnp_Locale", Locale.VIETNAM.getCode());

        params.put("vnp_OrderInfo", orderInfo);
        params.put("vnp_OrderType", ORDER_TYPE);


        var initPaymentUrl = buildInitPaymentUrl(params);

        return InitPaymentResponse.builder()
                .vnpUrl(initPaymentUrl)
                .build();
    }

    private String buildPaymentDetail(InitPaymentRequest request) {
        return String.format("Thanh toan don dat phong %s", request.getTxnRef());
    }

    private String buildReturnUrl(String txnRef) {
        return String.format(returnUrlFormat, txnRef);
    }

    @SneakyThrows
    private String buildInitPaymentUrl(Map<String, String> params) {
        var hashPayload = new StringBuilder();
        var query = new StringBuilder();
        var fieldNames = new ArrayList<>(params.keySet());
        Collections.sort(fieldNames);

        var itr = fieldNames.iterator();
        while (itr.hasNext()) {
            var fieldName = itr.next();
            var fieldValue = params.get(fieldName);
            if ((fieldValue != null) && (!fieldValue.isEmpty())) {
                //Build hash data
                hashPayload.append(fieldName);
                hashPayload.append(Symbol.EQUAL);
                hashPayload.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));

                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII));
                query.append(Symbol.EQUAL);
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                if (itr.hasNext()) {
                    query.append(Symbol.AND);
                    hashPayload.append(Symbol.AND);
                }
            }
        }

        var secureHash = cryptoService.sign(hashPayload.toString());

        query.append("&vnp_SecureHash=");
        query.append(secureHash);

        return initPaymentPrefixUrl + "?" + query;
    }
}
