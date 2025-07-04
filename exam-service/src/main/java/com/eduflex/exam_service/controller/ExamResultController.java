package com.eduflex.exam_service.controller;


import com.eduflex.exam_service.dto.ExamResultRequest;
import com.eduflex.exam_service.dto.ExamResultResponse;
import com.eduflex.exam_service.entity.ExamResult;
import com.eduflex.exam_service.service.ExamResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/exam-results")
@RequiredArgsConstructor
public class ExamResultController {

    private final ExamResultService examResultService;

    @PostMapping
    public ResponseEntity<String> submitResult(@RequestBody ExamResultRequest request) {
        examResultService.submitExamResult(request);
        return ResponseEntity.ok("Result submitted successfully");
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<ExamResultResponse>> getResultsByStudent(@PathVariable String studentId) {
        return ResponseEntity.ok(examResultService.getResultsByStudent(studentId));
    }

    @GetMapping("/exam/{examId}")
    public ResponseEntity<List<ExamResultResponse>> getResultsByExam(@PathVariable String examId) {
        return ResponseEntity.ok(examResultService.getResultsByExam(examId));
    }


    @GetMapping("/check")
    public ResponseEntity<Map<String, Object>> checkIfPassed(
        @RequestParam String userId,
        @RequestParam String courseId) {

    // 1. تأكّد إذا في نتائج أصلًا
    List<ExamResult> results = examResultService.findResults(userId, courseId);
    if (results.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(Map.of(
                "studentId", userId,
                "courseId", courseId,
                "passed", false,
                "message", "No exam results found for this student and course"
            ));
    }

    // 2. احسب النجاح
    boolean passed = results.stream().anyMatch(r -> r.getScore() >= 50);

    // 3. ارجع JSON مع الحالة
    return ResponseEntity.ok(Map.of(
        "studentId", userId,
        "courseId", courseId,
        "passed", passed,
        "message", passed
            ? "Congratulations! You passed the exam."
            : "Unfortunately, you did not pass the exam."
    ));
}

}
