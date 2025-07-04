package com.eduflex.payment_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        private String id;

        private String userId;
        private String courseId;
        private Double amount;

        private LocalDateTime paidAt;

        private String method; // مثل: "CARD", "PAYPAL", "CASH"
}

