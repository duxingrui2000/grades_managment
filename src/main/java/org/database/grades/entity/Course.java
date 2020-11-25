package org.database.grades.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * @author Yuqi
 */
@Entity
@Data
public class Course implements Serializable {
    @Id
    Long courseId;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    Subject subject;

    Integer classNumber;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    Teacher teacher;

    Short credit;

    String announcement;

    String requirement;


}
