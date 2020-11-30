package org.database.grades.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Subject {
    @Id
    Long subjectId;

    Short credit;

    String subjectName;
}
