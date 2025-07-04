package com.eduflex.exam_service.service;

import com.eduflex.exam_service.dto.ExamResultRequest;
import com.eduflex.exam_service.dto.ExamResultResponse;
import com.eduflex.exam_service.entity.ExamResult;
import com.eduflex.exam_service.repository.ExamResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExamResultService {

    private final ExamResultRepository examResultRepository;

    public void submitExamResult(ExamResultRequest request) {
        boolean passed = request.getScore() >= 50; // اجتياز إذا العلامة >= 50

        ExamResult result = ExamResult.builder()
            .examId(request.getExamId())
            .studentId(request.getStudentId())  
            .courseId(request.getCourseId())    
            .score(request.getScore())
            .passed(passed)
            .submittedAt(LocalDateTime.now())
            .build();

        examResultRepository.save(result);
    }

    public List<ExamResultResponse> getResultsByStudent(String studentId) {
        return examResultRepository.findByStudentId(studentId)
                .stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public List<ExamResultResponse> getResultsByExam(String examId) {
        return examResultRepository.findByExamId(examId)
                .stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    private ExamResultResponse mapToResponse(ExamResult result) {
        return ExamResultResponse.builder()
                .id(result.getId())
                .examId(result.getExamId())
                .studentId(result.getStudentId())
                .score(result.getScore())
                .passed(result.getPassed())
                .submittedAt(result.getSubmittedAt())
                .build();
    }


    public boolean hasUserPassedCourse(String studentId, String courseId) {
        List<ExamResult> results = examResultRepository.findByStudentIdAndCourseId(studentId, courseId);
        
        // افتراضًا: النجاح = نتيجة >= 50
        return results.stream().anyMatch(result -> result.getScore() >= 50);
    }


    public List<ExamResult> findResults(String studentId, String courseId) {
    return examResultRepository.findByStudentIdAndCourseId(studentId, courseId);
    }

}

