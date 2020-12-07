package org.database.grades.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class Subject {
    @Id
    @GeneratedValue
    Long subjectId;

    Short credit;

    String subjectName;
}
