package com.eduflex.exam_service.repository;

import com.eduflex.exam_service.entity.Exam;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExamRepository extends JpaRepository<Exam, String> {
    List<Exam> findByCourseId(String courseId);
}
