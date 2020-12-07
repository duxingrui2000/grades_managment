package org.database.grades.repository;

import org.database.grades.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubjectRepository extends JpaRepository<Subject,Long> {
    public Optional<Subject> findBySubjectName(String name);
}
