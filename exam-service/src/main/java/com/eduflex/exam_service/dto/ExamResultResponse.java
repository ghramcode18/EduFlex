package com.eduflex.exam_service.dto;

import lombok.*;

import java.time.LocalDateTime;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ExamResultResponse {
    private String id;
    private String examId;
    private String studentId;
    private Double score;
    private Boolean passed;
    private LocalDateTime submittedAt;
}