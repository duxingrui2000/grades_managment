package org.database.grades.repository;

import org.database.grades.entity.Course;
import org.database.grades.entity.Student;
import org.database.grades.entity.StudentCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface StudentCourseRepository extends JpaRepository<StudentCourse,StudentCourse.Key> {
    public Set<Course> findAllByStudent(Student student);

}
