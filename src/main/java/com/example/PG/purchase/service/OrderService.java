package com.example.PG.purchase.service;

import com.example.PG.purchase.PortOneApiClient;
import com.example.PG.purchase.controller.dto.WebhookPayload;
import com.example.PG.purchase.repository.OrderRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Map;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    @Value("${portone.webhook.secret}")
    private String WEBHOOK_SECRET;
    private final PortOneApiClient portOneApiClient;
    private final OrderRepository orderRepository;

    /**
     * 웹훅 처리 메인 메소드
     */
    public void processWebhook(String webhookId, String webhookSignature, String webhookTimestamp, String payload) {
        verifyTimestamp(webhookTimestamp); // 타임스탬프 검증
        String expectedSignature = generateSignature(webhookId, webhookTimestamp, payload); // 시그니처 생성
        if (!verifySignature(expectedSignature, webhookSignature)) { // 시그니처 비교
            throw new IllegalArgumentException("유효하지 않은 시그니처");
        }
        WebhookPayload data = parsePayload(payload); // 요청 본문 파싱
        handleEvent(data); // 이벤트 처리
    }

    /**
     * 타임스탬프 검증
     * 요청이 5분 이상 경과한 경우 무효화
     */
    private void verifyTimestamp(String timestamp) {
        long now = System.currentTimeMillis() / 1000; // 현재 시간 (초 단위)
        long requestTimestamp = Long.parseLong(timestamp); // 요청 시간
        if (Math.abs(now - requestTimestamp) > 300) { // 5분 = 300초
            throw new IllegalArgumentException("유효하지 않은 타임스탬프");
        }
    }

    /**
     * 요청 데이터 기반 시그니처 생성
     */
    private String generateSignature(String webhookId, String timestamp, String payload) {
        try {
            String dataToSign = String.join(".", webhookId, timestamp, payload); // 데이터 조합
            Mac mac = Mac.getInstance("HmacSHA256"); // HMAC-SHA256 알고리즘 사용
            mac.init(new SecretKeySpec(WEBHOOK_SECRET.getBytes(), "HmacSHA256")); // Secret 키 설정
            return Base64.getEncoder().encodeToString(mac.doFinal(dataToSign.getBytes())); // 시그니처 생성
        } catch (Exception e) {
            throw new RuntimeException("시그니처 생성 중 오류 발생", e);
        }
    }

    /**
     * 시그니처 비교
     * 생성된 시그니처와 포트원에서 제공된 시그니처가 동일한지 확인
     */
    private boolean verifySignature(String expectedSignature, String actualSignature) {
        return expectedSignature.equals(actualSignature);
    }

    /**
     * 웹훅 데이터 JSON 파싱
     */
    private WebhookPayload parsePayload(String payload) {
        try {
            return new ObjectMapper().readValue(payload, WebhookPayload.class); // Jackson 사용
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON 파싱 오류", e);
        }
    }

    /**
     * 이벤트 유형에 따른 처리
     */
    private void handleEvent(WebhookPayload data) {
        String type = data.getType(); // 이벤트 타입
        String paymentId = data.getData().getPaymentId(); // 결제 ID

        switch (type) {
            case "Transaction.Ready":
                log.info("결제창 오픈 이벤트 처리");
                break;
            case "Transaction.Paid":
                log.info("결제 완료 이벤트 처리");
                // TODO: 결제 금액 검증 & OrderStatus 변경 & SSE 전송
                break;
            case "Transaction.Cancelled":
                log.info("결제 취소 이벤트 처리");
                break;
            default:
                log.warn("알 수 없는 이벤트 타입: {}", type);
        }
    }
    public void verifyPayment(String paymentId, int i) {
        log.info("결제 검증 시작: paymentId = {}", paymentId);

        // 1. 포트원 API를 통해 결제 상세 내역 조회
        Map<String, Object> paymentData = portOneApiClient.getPaymentDetails(paymentId);

        if (paymentData == null) {
            throw new RuntimeException("포트원에서 결제 정보를 찾을 수 없습니다.");
        }

        // 2. 결제 상태 확인 (V2 기준 status가 PAID인지 확인)
        String status = (String) paymentData.get("status");
        if (!"PAID".equals(status)) {
            throw new RuntimeException("결제가 완료되지 않은 상태입니다. (status: " + status + ")");
        }

        // 3. 금액 검증 (DB의 주문 금액과 포트원의 결제 금액이 일치하는지)
        // Map 구조는 포트원 V2 응답 객체에 따라 다를 수 있으니 로그로 찍어 확인해보세요.
        Map<String, Object> amountData = (Map<String, Object>) paymentData.get("amount");
        int paidAmount = ((Number) amountData.get("total")).intValue();

        int expectedAmount = 30000; // 아까 Controller에서 설정한 가격

        if (paidAmount != expectedAmount) {
            // 금액이 다르면 해킹 위험이 있으므로 결제 취소 API를 호출하거나 예외를 발생시킵니다.
            log.error("금액 불일치! 해킹 의심: 기대금액 {}, 실제결제 {}", expectedAmount, paidAmount);
            throw new RuntimeException("결제 금액이 일치하지 않습니다.");
        }

        log.info("결제 검증 완료! 주문 상태를 변경합니다.");

        // 4. DB 상태 업데이트 및 회원 정보 변경 (게임 소유 여부 등)
        // orderRepository.updateStatus(paymentId, "SUCCESS");
        // memberService.updateHasGame(memberId, true);
    }
}