package com.eduflex.auth_service.dto;

import com.eduflex.auth_service.Enum.UserType;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    private String name;

    @Email
    private String email;

    private String phoneNumber;

    private String password;

    private UserType type;
}