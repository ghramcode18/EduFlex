package com.eduflex.course_service.service;

import com.eduflex.course_service.Enum.UserType;
import com.eduflex.course_service.dto.CourseRequest;
import com.eduflex.course_service.dto.CourseResponse;
import com.eduflex.course_service.dto.UserResponse;
import com.eduflex.course_service.entity.Course;
import com.eduflex.course_service.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseServices {
    private final RestTemplate restTemplate;
    private final CourseRepository courseRepository;

    public void createCourse(CourseRequest request) {
        Course course = Course.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .price(request.getPrice())
                .instructorId(request.getInstructorId())
                .isApproved(false) // بانتظار موافقة الأدمن
                .createdAt(LocalDateTime.now())
                .build();

        courseRepository.save(course);
    }

    public Boolean IsInstructorId(String instructorId ) throws BadRequestException {
        ResponseEntity<UserResponse> response;

        try {
            response = restTemplate.getForEntity(
                    "http://user-service/api/users/id/"
                            + instructorId,
                    UserResponse.class
            );
        } catch (Exception e) {
            throw new BadRequestException("User Checking failed: " + e.getMessage());
        }
        if (response.getBody().getType().equals(UserType.INSTRUCTOR))
            return true;
        else return false;
    }
    public List<CourseResponse> getApprovedCourses() {
        return courseRepository.findByIsApproved(true)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<CourseResponse> getCoursesByInstructor(String instructorId) {
        return courseRepository.findByInstructorId(instructorId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public void approveCourse(String courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        course.setApproved(true);
        courseRepository.save(course);
    }

    private CourseResponse mapToResponse(Course course) {
        return CourseResponse.builder()
                .id(course.getId())
                .title(course.getTitle())
                .description(course.getDescription())
                .price(course.getPrice())
                .instructorId(course.getInstructorId())
                .isApproved(course.isApproved())
                .build();
    }
}
