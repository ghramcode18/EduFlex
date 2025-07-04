package com.eduflex.auth_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EmailRequest {
    @NotBlank(message = "Email is required")
    @Email(message = "Email is invalid")
    private String email;
}
