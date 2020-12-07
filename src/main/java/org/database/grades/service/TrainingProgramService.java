package org.database.grades.service;

import org.database.grades.entity.TrainingProgram;

import java.util.List;

public interface TrainingProgramService {
    List<TrainingProgram> GetAllTrainingProgram() throws Exception;
    TrainingProgram GetTrainingProgramById(Long collegeId) throws Exception;
    void EditTrainingProgramService(TrainingProgram trainingProgram) throws Exception;
}
