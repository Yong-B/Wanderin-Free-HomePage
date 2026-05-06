package com.example.PG.purchase.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty; // 추가
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class WebhookPayload {
    private String type;
    private String timestamp;
    private WebhookData data;

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class WebhookData {
        // [수정] 포트원 V2는 payment_id (언더바)로 보냅니다.
        @JsonProperty("payment_id")
        private String paymentId;

        private String status;
    }
}