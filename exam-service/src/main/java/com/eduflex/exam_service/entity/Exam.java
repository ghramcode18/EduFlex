package com.eduflex.exam_service.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Exam {

        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        private String id;

        private String courseId;     // الدورة المرتبط بها الامتحان
        private String instructorId; // المدرّب الذي أنشأ الاختبار
        private String title;

        private LocalDateTime createdAt;

        private Boolean isActive; // يمكن تعطيله بعد اجتيازه
}
