package org.database.grades.service;

import org.database.grades.entity.Course;
import org.database.grades.entity.Student;

import java.util.List;

public interface StudentService {
    /**
     * @return 字符串形式的已选课程信息
     */
    public List<Course> getAllSelectedCourses(Long studentId) throws Exception;

    public List<Course> getAllUnfinishedCourses(Long studentId);

    public Student getStudentByUsername(String studentUsername) throws Exception;

    public Student getStudentById(Long studentId) throws Exception;

    public boolean isCompulsory(Student student,Course course);

    public void changePassword(Student student,String encodePassword);
    
    public List<Student> getAllStudents() throws Exception;
    public void EditStudentInfo(Student student)throws Exception;
}
