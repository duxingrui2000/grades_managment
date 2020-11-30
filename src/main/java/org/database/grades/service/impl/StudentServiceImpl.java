package org.database.grades.service.impl;

import org.database.grades.entity.Course;
import org.database.grades.entity.Student;
import org.database.grades.entity.StudentCourse;
import org.database.grades.repository.CourseRepository;
import org.database.grades.repository.StudentRepository;
import org.database.grades.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    CourseRepository courseRepository;

    @Override
    public Student getStudent(Long studentId) throws Exception {
        Optional<Student> student = this.studentRepository.findById(studentId);
        if (student.isEmpty())
            throw new Exception();
        return student.get();
    }

    @Override
    public List<Course> getAllSelectedCourses(Long studentId) throws Exception {
        return null;
    }

    @Override
    public List<Course> getAllUnfinishedCourses(Long studentId) {
        return null;
    }
}
