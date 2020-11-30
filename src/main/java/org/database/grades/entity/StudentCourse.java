package org.database.grades.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@IdClass(StudentCourse.Key.class)
@Table(name = "student_course")
public class StudentCourse implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "student_id")
    Student student;

    @Id
    @ManyToOne
    @JoinColumn(name = "course_id")
    Course course;

    Short finalScore;
    Short usualScore;
    Short attendance;

    @Data
    public static class Key implements Serializable {
        private Long student;
        private Long course;
    }

}
