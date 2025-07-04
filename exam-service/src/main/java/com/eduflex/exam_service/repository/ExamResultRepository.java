package com.eduflex.exam_service.repository;

import com.eduflex.exam_service.entity.ExamResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExamResultRepository extends JpaRepository<ExamResult, String> {
    List<ExamResult> findByStudentId(String studentId);
    List<ExamResult> findByExamId(String examId);
    List<ExamResult> findByStudentIdAndCourseId(String studentId, String courseId);

}
