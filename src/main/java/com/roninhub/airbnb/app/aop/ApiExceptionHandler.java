package com.roninhub.airbnb.app.aop;

import com.roninhub.airbnb.app.dto.response.ResponseDto;
import com.roninhub.airbnb.app.service.ResponseFactory;
import com.roninhub.airbnb.domain.common.constant.ResponseCode;
import com.roninhub.airbnb.domain.common.exception.BusinessException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class ApiExceptionHandler {

    private final ResponseFactory responseFactory;

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ResponseDto> handleBusinessException(BusinessException e,
                                                               HttpServletRequest request,
                                                               HttpServletResponse response) {
        ResponseCode code = e.getResponseCode();
        if (code != null) {
            return ResponseEntity
                    .status(code.getCode())
                    .body(responseFactory.response(code));
        }

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(responseFactory.response(ResponseCode.INTERNAL_SERVER_ERROR));
    }
}
