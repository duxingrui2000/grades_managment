package org.database.grades.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Data
@Entity
public class Teacher {
    @Id
    Long teacherId;

    @Column(nullable = false)
    String tName;

}