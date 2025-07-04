package com.eduflex.exam_service.dto;

import lombok.*;

@Setter
@Getter
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExamResultRequest {
    private String examId;
    private String studentId;
    private String userId;
    private String courseId;
    private Double score;
}
