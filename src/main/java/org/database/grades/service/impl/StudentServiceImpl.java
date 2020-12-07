package org.database.grades.service.impl;

import org.database.grades.entity.Course;
import org.database.grades.entity.Student;
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
    public Student getStudentByUsername(String studentUsername) throws Exception {
        Optional<Student> student = this.studentRepository.findByUsername(studentUsername);
        if (student.isEmpty())
            throw new Exception();
        return student.get();
    }

    @Override
    public Student getStudentById(Long studentId) throws Exception {
        Optional<Student> student = this.studentRepository.findById(studentId);
        if (student.isEmpty())
            throw new Exception();
        return student.get();
    }

    @Override
    public boolean isCompulsory(Student student, Course course) {
        return student.getTrainingProgram().getSubjects().contains(course.getSubject());
    }

    @Override
    public void changePassword(Student student, String encodePassword) {
        student.setPassword(encodePassword);
        studentRepository.save(student);
    }

    @Override
    public List<Course> getAllSelectedCourses(Long studentId) throws Exception {
        return null;
    }

    @Override
    public List<Course> getAllUnfinishedCourses(Long studentId) {
        return null;
    }
    
    @Override
    public List<Student> getAllStudents() throws Exception {
        return studentRepository.findAll();
    }
    
    @Override
    public void EditStudentInfo(Student student) throws Exception {
        studentRepository.save(student);
        studentRepository.flush();
    }
}
