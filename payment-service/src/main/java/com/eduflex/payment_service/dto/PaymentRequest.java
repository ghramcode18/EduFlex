package com.eduflex.payment_service.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequest {
    private String userId;
    private String courseId;
    private Double amount;
    private String method;
}
