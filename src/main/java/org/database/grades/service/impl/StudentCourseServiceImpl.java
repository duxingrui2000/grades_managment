package org.database.grades.service.impl;

import org.database.grades.entity.Course;
import org.database.grades.entity.Student;
import org.database.grades.entity.StudentCourse;
import org.database.grades.repository.StudentCourseRepository;
import org.database.grades.service.StudentCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class StudentCourseServiceImpl implements StudentCourseService {
    @Autowired
    StudentCourseRepository studentCourseRepository;

    @Override
    public List<StudentCourse> getAllStudentCourseByStudent(Student student) {
        return studentCourseRepository.findAllByStudent(student);
    }
    
    @Override
    public void EditStudentCourseInfo(StudentCourse studentCourse) {
        studentCourseRepository.save(studentCourse);
        studentCourseRepository.flush();
    }
}
