package com.eduflex.course_service.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Course {

        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        private String id;

        private String title;
        private String description;
        private Double price;

        private String instructorId;

        private boolean isApproved;

        private LocalDateTime createdAt;
}
