package org.database.grades.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

/**
 * @author Yuqi
 */
@Entity
@Data
public class Course {
    @Id
    Long courseId;

    @Column(unique = true)
    Integer classNumber;

    @Column(length = 20)
    String cName;

    @ManyToOne
    @JoinColumn(name = "teacherId")
    Teacher teacher;

    Short credit;

    String announcement;

    String requirement;

    @ManyToMany(mappedBy = "courses")
    Set<Student> students;
}
