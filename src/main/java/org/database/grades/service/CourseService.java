package org.database.grades.service;

import org.database.grades.entity.Course;

import java.util.List;

public interface CourseService {
    List<Course> findAllCoursesByStudentId(Long studentId) throws Exception;
}
