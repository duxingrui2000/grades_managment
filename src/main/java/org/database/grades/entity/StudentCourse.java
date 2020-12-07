package org.database.grades.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@Table(name = "student_course")
public class StudentCourse implements Serializable {
    @Id
    @GeneratedValue
    Long id;


    @ManyToOne
    @JoinColumn(name = "student_id")
    Student student;


    @ManyToOne
    @JoinColumn(name = "course_id")
    Course course;

    Short finalScore;
    Short usualScore;
    Short attendance;
}
