package com.eduflex.exam_service.controller;

import com.eduflex.exam_service.dto.ExamRequest;
import com.eduflex.exam_service.dto.ExamResponse;
import com.eduflex.exam_service.dto.MessageResponse;
import com.eduflex.exam_service.service.ExamService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exams")
@RequiredArgsConstructor
public class ExamController {

    private final ExamService examService;

    @PostMapping
    public ResponseEntity<MessageResponse> createExam(@RequestBody ExamRequest request) throws BadRequestException {

        if(examService.IsInstructorId(request.getInstructorId()))
        {
        examService.createExam(request);
        }
        else {
            return ResponseEntity.ok(new MessageResponse("You do not have authority, just Instructor can add courses to system"));
        }
        return ResponseEntity.ok(new MessageResponse("Exam created successfully"));
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<ExamResponse>> getExamsByCourse(@PathVariable String courseId) {
        return ResponseEntity.ok(examService.getExamsByCourse(courseId));
    }

    @PutMapping("/{examId}/deactivate")
    public ResponseEntity<String> deactivateExam(@PathVariable String examId) {
        examService.deactivateExam(examId);
        return ResponseEntity.ok("Exam deactivated");
    }
}
