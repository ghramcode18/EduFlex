package com.eduflex.auth_service.dto;

import com.eduflex.auth_service.Enum.UserType;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private String accessToken;
    private String refreshToken;
    private UserType type;
}
