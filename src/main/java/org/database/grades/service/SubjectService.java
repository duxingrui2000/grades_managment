package org.database.grades.service;

import org.database.grades.entity.Subject;

import java.util.List;

public interface SubjectService {
    List<Subject> findAllSubjects() throws Exception;
    void EditSubjectInfo(Subject subject) throws Exception;
    Subject findSubjectByName(String name) throws Exception;
}
