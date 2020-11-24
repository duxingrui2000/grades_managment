package org.database.grades.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity
@Data
public class TrainingProgram {
    @Id
    private String id;

    @ManyToMany
    private Set<Course> courses;

    @OneToMany(mappedBy = "trainingProgram")
    private Set<Student> students;

}
