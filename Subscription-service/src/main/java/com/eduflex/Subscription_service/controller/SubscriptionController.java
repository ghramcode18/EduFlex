package com.eduflex.Subscription_service.controller;

import com.eduflex.Subscription_service.Enum.SubscriptionStatus;
import com.eduflex.Subscription_service.dto.SubscriptionDto;
import com.eduflex.Subscription_service.dto.SubscriptionRequest;
import com.eduflex.Subscription_service.dto.SubscriptionResponse;
import com.eduflex.Subscription_service.repository.SubscriptionRepository;
import com.eduflex.Subscription_service.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;
    private final SubscriptionRepository subscriptionRepository;

    @PostMapping
    public ResponseEntity<String> subscribe(@RequestBody SubscriptionRequest request) {
        subscriptionService.subscribe(request);
        return ResponseEntity.ok("Subscribed successfully");
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<SubscriptionResponse>> getByUser(@PathVariable String userId) {
        return ResponseEntity.ok(subscriptionService.getSubscriptionsByUser(userId));
    }


    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<SubscriptionResponse>> getByCourse(@PathVariable String courseId) {
        return ResponseEntity.ok(subscriptionService.getSubscriptionsByCourse(courseId));
    }

    @GetMapping("/user/{userId}/course/{courseId}")
    public ResponseEntity<SubscriptionDto> getByUserAndCourse(
            @PathVariable String userId,
            @PathVariable String courseId
    ) {
        return subscriptionRepository.findByUserIdAndCourseId(userId, courseId)
                .map(subscription -> ResponseEntity.ok(
                        SubscriptionDto.builder()
                                .id(subscription.getId())
                                .userId(subscription.getUserId())
                                .courseId(subscription.getCourseId())
                                .status(subscription.getStatus())
                                .build()
                ))
                .orElse(ResponseEntity.notFound().build());

    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateSubscription(@PathVariable String id, @RequestBody SubscriptionDto dto) {
        return subscriptionRepository.findById(id).map(subscription -> {
            subscription.setStatus(SubscriptionStatus.valueOf(dto.getStatus().name()));
            subscription.setActive(true);
            subscriptionRepository.save(subscription);
            return ResponseEntity.ok().build();
        }).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/complete")
    public ResponseEntity<?> complete(@RequestParam String userId, @RequestParam String courseId) {
        subscriptionService.completeSubscriptionIfPassed(userId, courseId);
        return ResponseEntity.ok("Subscription marked as COMPLETED.");
    }
}

