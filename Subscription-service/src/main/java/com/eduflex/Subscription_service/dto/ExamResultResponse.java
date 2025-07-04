package com.eduflex.Subscription_service.dto;

import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class ExamResultResponse {
    private boolean passed;

}