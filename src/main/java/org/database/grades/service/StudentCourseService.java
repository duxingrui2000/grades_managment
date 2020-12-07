package org.database.grades.service;

import org.database.grades.entity.Student;
import org.database.grades.entity.StudentCourse;

import java.util.List;

public interface StudentCourseService {

    List<StudentCourse> getAllStudentCourseByStudent(Student student);
    void EditStudentCourseInfo(StudentCourse studentCourse);
}
