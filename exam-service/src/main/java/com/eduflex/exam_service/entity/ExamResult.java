package com.eduflex.exam_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExamResult {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String examId;
    private String studentId;
    
     
    private String courseId;

    private Double score;
    private Boolean passed;
    private LocalDateTime submittedAt;
}
