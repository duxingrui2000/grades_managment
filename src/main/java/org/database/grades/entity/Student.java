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

    @ManyToMany
    @JoinTable(name = "student_course",
            joinColumns = @JoinColumn(name = "sId"),
            inverseJoinColumns = @JoinColumn(name = "courseId"))
    Set<Course> selectedCourses;

    @ManyToOne
    @JoinTable(name = "student_training")
    TrainingProgram trainingProgram;

}
