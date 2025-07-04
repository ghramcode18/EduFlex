package com.eduflex.auth_service.dto;

import com.eduflex.auth_service.Enum.UserType;
import jakarta.validation.constraints.*;
import lombok.*;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateUserRequest {
    private String name;
    @Email
    private String email;
    private String phoneNumber;
    private UserType type;
    private String password;
}
