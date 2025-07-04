package com.eduflex.course_service.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseResponse {
    private String id;
    private String title;
    private String description;
    private Double price;
    private String instructorId;
    private boolean isApproved;
}