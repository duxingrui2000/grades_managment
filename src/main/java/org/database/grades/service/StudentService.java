package org.database.grades.service;

import org.database.grades.entity.Course;
import org.database.grades.entity.Student;
import org.database.grades.entity.StudentCourse;
import org.database.grades.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

public interface StudentService {
    /**
     *
     * @return 字符串形式的已选课程信息
     */
    public List<Course> getAllSelectedCourses(Long studentId) throws Exception;

    public List<Course> getAllUnfinishedCourses(Long studentId);

    public Student getStudent(Long studentId) throws Exception;
}
