package org.database.grades.repository;

import org.database.grades.entity.Student;
import org.database.grades.entity.StudentCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentCourseRepository extends JpaRepository<StudentCourse,Long> {
    public List<StudentCourse> findAllByStudent(Student student);

    public List<StudentCourse> findAllByStudentAndFinalScoreIsNotNull(Student student);
}
