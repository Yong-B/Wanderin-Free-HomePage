package com.example.PG.purchase;

import com.example.PG.purchase.controller.dto.CancellationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class PortOneApiClient {
    
    private final WebClient webClient;

    @Value("${portone.api-base-url}")
    private String baseUrl;

    @Value("${portone.api-secret}")
    private String apiToken;

    /**
     * 단건 조회 API 호출
     *
     * @param paymentId 결제 ID
     * @return 결제 상세 정보 (Map 형태)
     */
    public Map<String, Object> getPaymentDetails(String paymentId) {
        return webClient
                .get()
                .uri(baseUrl + "/payments/{paymentId}", paymentId)
                .header("Authorization", "PortOne " + apiToken)
                .retrieve()
                .bodyToMono(Map.class)
                .block();
    }
    
    public CancellationResponse cancelOrder(String paymentId) {

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("reason", "고객 요청");

        CancellationResponse result = webClient
                .post()
                .uri(baseUrl + "/{paymentId}/cancel", paymentId)
                .header("Authorization", "PortOne " + apiToken)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(CancellationResponse.class)
                .block();

        return result;
    }
}