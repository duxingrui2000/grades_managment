package org.database.grades.entity;

import lombok.Data;

import javax.persistence.*;

@Table(name = "student_course")
@Data
public class StudentCourse {


    @Column
    Short score;

}
