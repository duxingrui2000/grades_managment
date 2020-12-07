package org.database.grades.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * @author Yuqi
 */
@Entity
@Data
public class Course implements Serializable {
    @Id
    @GeneratedValue
    Long courseId;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    Subject subject;

    Integer classNumber;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    Teacher teacher;

    @OneToMany(mappedBy = "course")
    Set<StudentCourse> studentCourses;

    String announcement;

    String requirement;
    
}
