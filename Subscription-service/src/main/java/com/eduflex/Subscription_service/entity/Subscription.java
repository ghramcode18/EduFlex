package com.eduflex.Subscription_service.entity;

import com.eduflex.Subscription_service.Enum.SubscriptionStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Subscription {

        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        private String id;

        private String userId;
        private String courseId;
        private LocalDateTime subscribedAt;
        private SubscriptionStatus status;
        private boolean active; // ممكن نستخدمها لاحقاً للإلغاء 
}
