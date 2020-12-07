package org.database.grades.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.Set;

@Entity
@Getter
@Setter
public class TrainingProgram {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ManyToMany
    private Set<Subject> subjects;

}
