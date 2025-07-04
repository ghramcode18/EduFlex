package com.eduflex.user_service.dto;
import lombok.*;


@Data
@Builder
@Setter
@Getter
public class CourseResponse {
    private String id;
    private String title;
    private String description;
    private Double price;
    private String instructorId;
    private boolean isApproved;
}
