package com.eduflex.exam_service.dto;

import com.eduflex.exam_service.Enum.UserType;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private String name;
    private String email;
    private String phoneNumber;
    private UserType type;
}