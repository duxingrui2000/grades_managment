package org.database.grades.service;

import org.database.grades.entity.Teacher;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

public interface TeacherService {
    public Teacher getTeacherByID(Long teacherId) throws Exception;
    public Teacher getTeacherByUsername(String teacherUsername) throws Exception;
    public List<Teacher> getAllTeachers() throws Exception;
    public void EditTeacherInfo(Teacher teacher) throws Exception;
}
