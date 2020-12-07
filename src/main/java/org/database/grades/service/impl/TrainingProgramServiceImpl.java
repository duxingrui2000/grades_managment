package org.database.grades.service.impl;

import org.database.grades.entity.TrainingProgram;
import org.database.grades.repository.TrainingProgramRepository;
import org.database.grades.service.TrainingProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrainingProgramServiceImpl implements TrainingProgramService {
    @Autowired
    TrainingProgramRepository trainingProgramRepository;
    @Override
    public List<TrainingProgram> GetAllTrainingProgram() throws Exception {
        return trainingProgramRepository.findAll();
    }
    
    @Override
    public void EditTrainingProgramService(TrainingProgram trainingProgram) throws Exception {
        trainingProgramRepository.save(trainingProgram);
    }
    
    @Override
    public TrainingProgram GetTrainingProgramById(Long collegeId) throws Exception {
        Optional<TrainingProgram> trainingProgram = this.trainingProgramRepository.findById(collegeId);
        if(trainingProgram.isEmpty()){
            throw new Exception();
        }
        return trainingProgram.get();
    }
}
