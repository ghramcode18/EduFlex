package com.eduflex.payment_service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.eduflex.payment_service.dto.PaymentRequest;
import com.eduflex.payment_service.dto.PaymentResponse;
import com.eduflex.payment_service.service.PaymentService;
import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<String> pay(@RequestBody PaymentRequest request) {
        paymentService.processPayment(request);
        return ResponseEntity.ok("Payment processed successfully");
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PaymentResponse>> getUserPayments(@PathVariable String userId) {
        return ResponseEntity.ok(paymentService.getUserPayments(userId));
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<PaymentResponse>> getCoursePayments(@PathVariable String courseId) {
        return ResponseEntity.ok(paymentService.getCoursePayments(courseId));
    }
}
