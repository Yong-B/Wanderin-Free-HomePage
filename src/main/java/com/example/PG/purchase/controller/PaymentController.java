package com.example.PG.purchase.controller;

import com.example.PG.purchase.service.OrderService;
import com.example.PG.user.member.domain.Member;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/purchase")
public class PaymentController {

    private final OrderService orderService;

    @PostMapping("/prepare")
    public ResponseEntity<Map<String, Object>> preparePayment(HttpSession session) {
        Member loginMember = (Member) session.getAttribute("loginMember");
        if (loginMember == null) return ResponseEntity.status(401).build();

        Map<String, Object> response = new HashMap<>();
        String merchantUid = "order-" + UUID.randomUUID().toString();
        int amount = 30000; // 설정한 가격

        response.put("merchantUid", merchantUid);
        response.put("amount", amount);

        log.info("주문 준비: {}, 금액: {}", merchantUid, amount);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/complete")
    public ResponseEntity<String> completePayment(@RequestBody Map<String, String> request, HttpSession session) {
        String paymentId = request.get("paymentId");
        Member loginMember = (Member) session.getAttribute("loginMember");

        // 1. 결제 검증 (30,000원 결제됐는지 포트원에 확인)
        orderService.verifyPayment(paymentId, 30000);

        // 2. [추가 로직] 성공 시 DB 업데이트 (예: 유저의 게임 권한 부여)
        // memberService.grantGameAccess(loginMember.getId());
        log.info("유저 {} 결제 완료 및 권한 부여", loginMember.getName());

        return ResponseEntity.ok("Success");
    }

    @PostMapping("/webhook")
    public ResponseEntity<String> handleWebhook(
            @RequestBody String payload,
            @RequestHeader("webhook-id") String webhookId,
            @RequestHeader("webhook-signature") String webhookSignature,
            @RequestHeader("webhook-timestamp") String webhookTimestamp
    ) {
        orderService.processWebhook(webhookId, webhookSignature, webhookTimestamp, payload);
        return ResponseEntity.ok("OK");
    }
}