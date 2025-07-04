package com.eduflex.Subscription_service.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionRequest {
    private String userId;
    private String courseId;
}
