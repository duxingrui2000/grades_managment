package org.database.grades.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
public class TrainingProgram {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ManyToMany
    private Set<Subject> subjects;

}
