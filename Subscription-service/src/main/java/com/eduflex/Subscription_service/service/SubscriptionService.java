package com.eduflex.Subscription_service.service;

import com.eduflex.Subscription_service.Enum.SubscriptionStatus;
import com.eduflex.Subscription_service.dto.SubscriptionRequest;
import com.eduflex.Subscription_service.dto.SubscriptionResponse;
import com.eduflex.Subscription_service.dto.ExamResultResponse;
import com.eduflex.Subscription_service.entity.Subscription;
import com.eduflex.Subscription_service.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    @Autowired
    private RestTemplate restTemplate;

    public void subscribe(SubscriptionRequest request) {
        if (subscriptionRepository.existsByUserIdAndCourseId(request.getUserId(), request.getCourseId())) {
            throw new RuntimeException("User already subscribed to this course");
        }

        Subscription subscription = Subscription.builder()
                .userId(request.getUserId())
                .courseId(request.getCourseId())
                .subscribedAt(LocalDateTime.now())
                .status(SubscriptionStatus.PENDING_PAYMENT)
                .build();

        subscriptionRepository.save(subscription);
    }


    public List<SubscriptionResponse> getSubscriptionsByUser(String userId) {
        return subscriptionRepository.findByUserId(userId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<SubscriptionResponse>getSubscriptionsById(String subscriptionId)     {
        return subscriptionRepository.findById(subscriptionId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
}
    public List<SubscriptionResponse> getSubscriptionsByCourse(String courseId) {
        return subscriptionRepository.findByCourseId(courseId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private SubscriptionResponse mapToResponse(Subscription subscription) {
        return SubscriptionResponse.builder()
                .id(subscription.getId())
                .userId(subscription.getUserId())
                .courseId(subscription.getCourseId())
                .status(subscription.getStatus())
                .active(subscription.isActive())
                .subscribedAt(subscription.getSubscribedAt())
                .build();
    }


     public void completeSubscriptionIfPassed(String userId, String courseId) {
        System.out.println(userId + " " +  courseId);
        Subscription subscription = subscriptionRepository
                .findByUserIdAndCourseId(userId, courseId)  
                .orElseThrow(() -> 
                            new RuntimeException("Subscription not found"));

        // الاتصال بـ exam-result-service
        String url = String.format("http://exam-service/api/exam-results/check?userId=%s&courseId=%s",
                            userId, courseId);

        ResponseEntity<ExamResultResponse> response = restTemplate.getForEntity(url, ExamResultResponse.class);

        if (Boolean.TRUE.equals(response.getBody().isPassed())) {
            subscription.setStatus(SubscriptionStatus.COMPLETED);
            subscriptionRepository.save(subscription);
        } else {
            throw new RuntimeException("User has not passed the exam yet");
        }
    }
}
