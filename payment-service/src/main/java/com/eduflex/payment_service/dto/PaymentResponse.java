package com.eduflex.payment_service.dto;

import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentResponse {
    private String id;
    private String userId;
    private String courseId;
    private Double amount;
    private String method;
    private LocalDateTime paidAt;
}
