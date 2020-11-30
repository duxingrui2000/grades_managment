package org.database.grades.service.impl;

import org.database.grades.entity.Course;
import org.database.grades.entity.Student;
import org.database.grades.entity.StudentCourse;
import org.database.grades.repository.CourseRepository;
import org.database.grades.service.CourseService;
import org.database.grades.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    CourseRepository courseRepository;

    @Autowired
    StudentService studentService;

    @Override
    public List<Course> findAllCoursesByStudentId(Long studentId) throws Exception {
        Student student = studentService.getStudent(studentId);
        Set<StudentCourse> studentCourses = student.getStudentCourses();
        List<Course> courses = new ArrayList<>();
        for(var i:studentCourses){
            courses.add(i.getCourse());
        }
        return courses;
    }
}
