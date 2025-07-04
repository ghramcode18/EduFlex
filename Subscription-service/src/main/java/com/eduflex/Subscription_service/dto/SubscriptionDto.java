package com.eduflex.Subscription_service.dto;

import lombok.*;
import com.eduflex.Subscription_service.Enum.SubscriptionStatus;

import java.time.LocalDateTime;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class SubscriptionDto {
    private String id;
    private String userId;
    private String  courseId;
    private SubscriptionStatus status;
}