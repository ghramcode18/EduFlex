package com.eduflex.payment_service.dto;


import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class SubscriptionDto {
    private String id;
    private String userId;
    private String courseId;
    private String status;
    private boolean active;
}