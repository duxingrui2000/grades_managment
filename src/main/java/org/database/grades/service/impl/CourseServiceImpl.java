package org.database.grades.service.impl;

import org.database.grades.entity.*;
import org.database.grades.repository.CourseRepository;
import org.database.grades.repository.StudentCourseRepository;
import org.database.grades.service.CourseService;
import org.database.grades.service.StudentService;
import org.database.grades.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    CourseRepository courseRepository;

    @Autowired
    StudentCourseRepository studentCourseRepository;

    @Autowired
    StudentService studentService;
    
    @Autowired
    TeacherService teacherService;
    
    @Override
    public List<Course> findAllCoursesByStudentId(Long studentId) throws Exception {
        Student student = studentService.getStudentById(studentId);
        List<StudentCourse> studentCourses = studentCourseRepository.findAllByStudent(student);
        List<Course> courses = new ArrayList<>();
        for(var i:studentCourses){
            courses.add(i.getCourse());
        }
        return courses;
    }
    
    @Override
    public List<Course> findAllCoursesByTeacherId(Long teacherId) throws Exception {
        Teacher teacher = teacherService.getTeacherByID(teacherId);
        return courseRepository.findAllByTeacher(teacher);
    }
    @Override
    public void EditRequirement_Announcement(Course course)
    {
        courseRepository.save(course);
    }
    
    @Override
    public List<Course> findAllCourses() throws Exception {
        return courseRepository.findAll();
    }
    
    @Override
    public Course findCourseByCourseId(Long courseId){
//        Course course =
//        if(courseRepository.findById(courseId).isPresent()){
//            return
//        }
        Optional<Course> byId = courseRepository.findById(courseId);
        return byId.get();
    }
    
    @Override
    public List<Course> findAllBySubject(Subject subject) throws Exception {
        return courseRepository.findAllBySubject(subject);
    }
}
