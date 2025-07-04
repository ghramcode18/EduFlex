package com.eduflex.course_service.repository;

import com.eduflex.course_service.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, String> {
    List<Course> findByInstructorId(String instructorId);
    List<Course> findByIsApproved(boolean isApproved);
}
