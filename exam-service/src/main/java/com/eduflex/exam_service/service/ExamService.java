package com.eduflex.exam_service.service;


import com.eduflex.exam_service.Enum.UserType;
import com.eduflex.exam_service.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import com.eduflex.exam_service.dto.ExamRequest;
import com.eduflex.exam_service.dto.ExamResponse;
import com.eduflex.exam_service.entity.Exam;
import com.eduflex.exam_service.repository.ExamRepository;
import org.springframework.web.client.RestTemplate;


@Service
@RequiredArgsConstructor
public class ExamService {

    private final RestTemplate restTemplate;
    private final ExamRepository examRepository;

    public void createExam(ExamRequest request) {
        Exam exam = Exam.builder()
                .title(request.getTitle())
                .courseId(request.getCourseId())
                .instructorId(request.getInstructorId())
                .createdAt(LocalDateTime.now())
                .isActive(true)
                .build();

        examRepository.save(exam);
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

    public List<ExamResponse> getExamsByCourse(String courseId) {
        return examRepository.findByCourseId(courseId)
                .stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public void deactivateExam(String examId) {
        examRepository.findById(examId).ifPresent(exam -> {
            exam.setIsActive(false);
            examRepository.save(exam);
        });
    }

    private ExamResponse mapToResponse(Exam exam) {
        return ExamResponse.builder()
                .id(exam.getId())
                .courseId(exam.getCourseId())
                .instructorId(exam.getInstructorId())
                .title(exam.getTitle())
                .createdAt(exam.getCreatedAt())
                .isActive(exam.getIsActive())
                .build();
    }
}
