package org.database.grades.service.impl;

import org.database.grades.entity.Subject;
import org.database.grades.repository.SubjectRepository;
import org.database.grades.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubjectServiceImpl implements SubjectService {
    @Autowired
    SubjectRepository subjectRepository;
    @Override
    public List<Subject> findAllSubjects() throws Exception {
        return subjectRepository.findAll();
    }
    
    @Override
    public void EditSubjectInfo(Subject subject) throws Exception {
        subjectRepository.save(subject);
        subjectRepository.flush();
    }
    
    @Override
    public Subject findSubjectByName(String name) throws Exception {
        Optional<Subject> new_s = subjectRepository.findBySubjectName(name);
        if(new_s.isEmpty()){
            throw new Exception();
        }
        return new_s.get();
    }
}
