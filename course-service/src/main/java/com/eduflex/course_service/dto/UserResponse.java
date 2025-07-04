package com.eduflex.course_service.dto;
import com.eduflex.course_service.Enum.UserType;
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
