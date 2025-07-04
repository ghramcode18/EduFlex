package com.eduflex.exam_service.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ExamResponse {
    private String id;
    private String courseId;
    private String instructorId;
    private String title;
    private LocalDateTime createdAt;
    private Boolean isActive;
}
