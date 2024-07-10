package com.roninhub.airbnb.app.service;

import com.roninhub.airbnb.app.dto.response.ApiError;
import com.roninhub.airbnb.app.dto.response.Meta;
import com.roninhub.airbnb.app.dto.response.ResponseDto;
import com.roninhub.airbnb.domain.common.constant.ResponseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class ResponseFactory {

    @Value("${spring.application.name}")
    String appName;


    public ResponseDto response(ResponseCode responseCode) {
        var meta = Meta.builder()
                .status(responseCode.getType())
                .serviceId(appName)
                .build();

        return new ResponseDto(meta, null);
    }

    public ResponseDto response(Object payload) {
        var meta = Meta.builder()
                .status(ResponseCode.SUCCESS.getType())
                .serviceId(appName)
                .build();

        return new ResponseDto(meta, payload);
    }


    public ResponseDto invalidParams(Collection<ApiError> errors) {
        var meta = Meta.builder()
                .status(ResponseCode.INVALID_PARAMS.getType())
                .serviceId(appName)
                .errors(errors)
                .build();

        return new ResponseDto(meta, null);
    }
}
