package com.eduflex.user_service.dto;

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

    private String type;

    private String role;
}