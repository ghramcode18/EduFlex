package com.eduflex.payment_service.service;


import com.eduflex.payment_service.dto.SubscriptionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import com.eduflex.payment_service.dto.PaymentRequest;
import com.eduflex.payment_service.dto.PaymentResponse;
import com.eduflex.payment_service.entity.Payment;
import com.eduflex.payment_service.repository.PaymentRepository;
import org.springframework.web.client.RestTemplate;


@Service
@RequiredArgsConstructor
public class PaymentService {

    @Autowired
    private RestTemplate restTemplate;
    private final PaymentRepository paymentRepository;

    public void processPayment(PaymentRequest request) {
        // 1. حفظ الدفع
        Payment payment = Payment.builder()
                .userId(request.getUserId())
                .courseId(request.getCourseId())
                .amount(request.getAmount())
                .method(request.getMethod())
                .paidAt(LocalDateTime.now())
                .build();
        paymentRepository.save(payment);

        try {
            // 2. استدعاء الاشتراك من subscription-service
            String fetchUrl = "http://Subscription-service/api/subscriptions/user/" + request.getUserId()
                    + "/course/" + request.getCourseId();
            ResponseEntity<SubscriptionDto> response = restTemplate.getForEntity(fetchUrl, SubscriptionDto.class);
            SubscriptionDto subscription = response.getBody();

            if (subscription == null) {
                throw new RuntimeException("Subscription not found for this user and course");
            }

            // 3. تحديث الحالة إلى PAID
            subscription.setStatus("PAID");
            subscription.setActive(true);

            // 4. إرسال تحديث الاشتراك
            String updateUrl =  "http://Subscription-service/api/subscriptions/" + subscription.getId();
            restTemplate.put(updateUrl, subscription);

        } catch (Exception e) {
            throw new RuntimeException("❌ Failed to update subscription: " + e.getMessage());
        }
    }

    public List<PaymentResponse> getUserPayments(String userId) {
        return paymentRepository.findByUserId(userId)
                .stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public List<PaymentResponse> getCoursePayments(String courseId) {
        return paymentRepository.findByCourseId(courseId)
                .stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    private PaymentResponse mapToResponse(Payment payment) {
        return PaymentResponse.builder()
                .id(payment.getId())
                .userId(payment.getUserId())
                .courseId(payment.getCourseId())
                .amount(payment.getAmount())
                .method(payment.getMethod())
                .paidAt(payment.getPaidAt())
                .build();
    }
}
