package org.database.grades.service;

import org.database.grades.entity.Course;
import org.database.grades.entity.Subject;

import java.util.List;

public interface CourseService {
    List<Course> findAllCoursesByStudentId(Long studentId) throws Exception;
    List<Course> findAllCoursesByTeacherId(Long teacherId) throws Exception;
    List<Course> findAllCourses() throws Exception;
    List<Course> findAllBySubject(Subject subject) throws Exception;
    Course findCourseByCourseId(Long courseId);
    void EditRequirement_Announcement(Course course) throws Exception;
    Double getAverageScore(Course course);
    Double getPassRate(Course course);
    Double getExcellentRate(Course course);

}
