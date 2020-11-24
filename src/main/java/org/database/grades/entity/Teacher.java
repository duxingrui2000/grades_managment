package org.database.grades.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Teacher {
    @Id
    Long teacherId;

    String tName;

}
