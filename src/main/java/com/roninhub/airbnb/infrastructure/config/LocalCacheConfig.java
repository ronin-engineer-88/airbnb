package com.roninhub.airbnb.infrastructure.config;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LocalCacheConfig {

    @Bean
    public Cache<String, String> localCache() {
        // Misconfiguration: without specifying any maximum size or expiration
        return CacheBuilder.newBuilder().build();
    }
}
