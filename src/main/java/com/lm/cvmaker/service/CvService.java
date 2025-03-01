package com.lm.cvmaker.service;

import com.lm.cvmaker.model.Cv;
import com.lm.cvmaker.persistence.CvRepository;
import com.lm.cvmaker.persistence.EducationRepository;
import com.lm.cvmaker.persistence.ExperienceRepository;
import com.lm.cvmaker.persistence.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CvService {

    private final CvRepository cvRepository;
    private final ProjectRepository projectRepository;
    private final ExperienceRepository experienceRepository;
    private final EducationRepository educationRepository;
    private final HugginFaceService hugginFaceService;

    public Cv createCv(Cv cv){

        String generatedSummary = hugginFaceService.generateSummary(cv);
        cv.setSummary(generatedSummary);
        Cv savedCv = cvRepository.save(cv);

        cv.getExperiences().forEach(exp ->{
            exp.setCv(savedCv);
            experienceRepository.save(exp);
        });

        cv.getEducation().forEach(edu ->{
            edu.setCv(savedCv);
            educationRepository.save(edu);
        });

        cv.getProjects().forEach(project -> {
            project.setCv(savedCv);
            projectRepository.save(project);
        });

        return savedCv;
    }

    public Optional<Cv> getCvById(Long id){
        return cvRepository.findById(id);
    }
    public List<Cv> getAllCvs(){
        return cvRepository.findAll();
    }
    public void deleteCv(Long id){
        cvRepository.deleteById(id);
    }

}
