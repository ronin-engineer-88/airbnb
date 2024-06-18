package com.roninhub.airbnb.app.service;

import com.roninhub.airbnb.app.dto.response.Meta;
import com.roninhub.airbnb.app.dto.response.ResponseDto;
import com.roninhub.airbnb.domain.common.constant.ResponseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResponseFactory {

    @Value("${spring.application.name}")
    String applicationShortName;


    public ResponseDto response(ResponseCode responseCode) {
        var meta = Meta.builder()
                .type(responseCode.getType())
                .build();

        return new ResponseDto(meta, null);
    }
}
