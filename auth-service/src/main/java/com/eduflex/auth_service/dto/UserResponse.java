package com.eduflex.auth_service.dto;

import com.eduflex.auth_service.Enum.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private String name;
    private String email;
    private String phoneNumber;
    private UserType type;
    private String password;
}

