package org.database.grades.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class Subject {
    @Id
    @GeneratedValue
    Long subjectId;

    Short credit;

    String subjectName;
}
