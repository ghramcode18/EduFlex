package com.eduflex.exam_service.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ExamRequest {
    private String courseId;
    private String instructorId;
    private String title;
}
