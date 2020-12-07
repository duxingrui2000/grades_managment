package org.database.grades.repository;

import org.database.grades.entity.Course;
import org.database.grades.entity.Subject;
import org.database.grades.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CourseRepository extends JpaRepository<Course,Long> {
    public List<Course> findAllByTeacher(Teacher teacher);
    List<Course> findAllBySubject(Subject subject);
}
