package com.eduflex.user_service.dto;

import com.eduflex.user_service.Enum.UserType;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequest {
    private String name;
    private String email;
    private String phoneNumber;
    private String password;
    private UserType type;
}

