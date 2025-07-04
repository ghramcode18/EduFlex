package com.eduflex.user_service.entity;

import com.eduflex.user_service.Enum.UserType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String name;

        @Column(unique = true)
        @Email
        private String email;

        private String phoneNumber;

        private  String password;

        private UserType type;

        private boolean isActive;

        private LocalDateTime createdAt;

        private LocalDateTime updatedAt;
}
