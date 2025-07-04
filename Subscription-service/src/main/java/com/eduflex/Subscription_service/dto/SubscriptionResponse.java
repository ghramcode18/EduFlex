package com.eduflex.Subscription_service.dto;

import lombok.*;
import com.eduflex.Subscription_service.Enum.SubscriptionStatus;
import java.time.LocalDateTime;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class SubscriptionResponse {
    private String id;
    private String userId;
    private String courseId;
    private boolean active;
    private SubscriptionStatus status;
    private LocalDateTime subscribedAt;
}