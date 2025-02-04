package com.roninhub.airbnb.infrastructure.config;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class LocalCacheConfig {

    @Bean
    public Cache<String, String> localCache() {
        // Misconfiguration: without specifying any maximum size or expiration
        return CacheBuilder.newBuilder()
                .maximumSize(10000L)
                .expireAfterWrite(3, TimeUnit.SECONDS)  // Just for reference, not for production
                .build();
    }
}
