package org.database.grades.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
public class Student {
    @Id
    @GeneratedValue
    Long studentId;


    @Column(length = 15)
    String sName;

    Boolean gender;

//    @OneToMany
//    @JoinTable(name = "student_course")
//    Set<StudentCourse> studentCourses;

    @ManyToOne
    TrainingProgram trainingProgram;

}
