package org.database.grades.service.impl;

import org.database.grades.entity.Teacher;
import org.database.grades.repository.CourseRepository;
import org.database.grades.repository.TeacherRepository;
import org.database.grades.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Service
public class TeacherServiceImpl implements TeacherService {
    @Autowired
    TeacherRepository teacherRepository;
    @Autowired
    CourseRepository courseRepository;
    
    
    @Override
    public Teacher getTeacherByID(Long teacherId) throws Exception {
        Optional<Teacher> teacher = this.teacherRepository.findById(teacherId);
        if(teacher.isEmpty())
            throw new Exception();
        return teacher.get();
    }
    
    @Override
    public Teacher getTeacherByUsername(String teacherUsername) throws Exception {
        Optional<Teacher> teacher = this.teacherRepository.findByUsername(teacherUsername);
        if(teacher.isEmpty())
            throw new Exception();
        return teacher.get();
    }
    
    @Override
    public List<Teacher> getAllTeachers() throws Exception {
        return teacherRepository.findAll();
    }
    
    @Override
    public void EditTeacherInfo(Teacher teacher) throws Exception {
        teacherRepository.save(teacher);
        teacherRepository.flush();
    }
}
