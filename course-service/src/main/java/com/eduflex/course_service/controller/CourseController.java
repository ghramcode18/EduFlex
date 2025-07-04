package com.eduflex.course_service.controller;


import com.eduflex.course_service.dto.CourseRequest;
import com.eduflex.course_service.dto.CourseResponse;
import com.eduflex.course_service.dto.MessageResponse;
import com.eduflex.course_service.service.CourseServices;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseServices courseService;

    // إنشاء دورة من قبل المدرّب
    @PostMapping
    public ResponseEntity<MessageResponse> createCourse(@RequestBody CourseRequest request) throws BadRequestException {
        if(courseService.IsInstructorId(request.getInstructorId()))
        {
            courseService.createCourse(request);
        }
        else {
            return ResponseEntity.ok(new MessageResponse("You do not have authority, just Instructor can add courses to system"));
        }
        return ResponseEntity.ok(new MessageResponse("Course created and waiting for approval"));
    }

    // عرض الدورات المعتمدة فقط
    @GetMapping("/approved")
    public ResponseEntity<List<CourseResponse>> getApprovedCourses() {
        return ResponseEntity.ok(courseService.getApprovedCourses());
    }

    // عرض دورات مدرب معيّن
    @GetMapping("/instructor/{instructorId}")
    public ResponseEntity<List<CourseResponse>> getInstructorCourses(@PathVariable String instructorId) {
        return ResponseEntity.ok(courseService.getCoursesByInstructor(instructorId));
    }

    // الموافقة على دورة (من قبل الأدمن فقط)
    @PutMapping("/{courseId}/approve")
    public ResponseEntity<String> approveCourse(@PathVariable String courseId) {
        courseService.approveCourse(courseId);
        return ResponseEntity.ok("Course approved successfully");
    }
}
