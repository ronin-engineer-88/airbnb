package com.roninhub.airbnb.api.kafka;


import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.RetriableException;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.retrytopic.DltStrategy;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaConsumer {


    @RetryableTopic(    // Non-blocking
            attempts = "4", // 1 primary topic + 3 retry topics + 1 DLT topic
            backoff = @Backoff(delay = 1000, multiplier = 2), // exponential backoff is better than linear backoff here - 1, 2, 4
            dltStrategy = DltStrategy.FAIL_ON_ERROR, // No retry on DLT topic
            autoCreateTopics = "true",  // true: for test; false: recommended
            include = {RetriableException.class, RuntimeException.class}    // RuntimeException: for test; mark retriable business exceptions
    )
    @KafkaListener(
            topics = "${kafka.booking.topic}",
            concurrency = "${kafka.booking.concurrency}"
    )
    void listenBookingCommand(@Payload String message) {
        log.info("Received Booking Command: {}", message);
        // Business Logic here
        // Test
        throw new RuntimeException("Test");
    }

    @DltHandler
    void retryBookingCommand(@Payload String message) {
        log.error("Alert Booking Command: {}", message);
    }
}
