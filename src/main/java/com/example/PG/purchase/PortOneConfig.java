package com.example.PG.purchase;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class PortOneConfig {

    @Value("${portone.store-id}")
    private String storeId;

    @Value("${portone.channel-key}")
    private String channelKey;

    @Value("${portone.api-secret}")
    private String apiSecret;

    @Value("${portone.webhook.secret}")
    private String webhookSecret;

    @Value("${portone.api-base-url}")
    private String baseUrl;
    
}