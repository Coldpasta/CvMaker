package com.lm.cvmaker.service;

import com.lm.cvmaker.model.Cv;
import com.lm.cvmaker.model.CvRequest;
import com.lm.cvmaker.model.Experience;
import com.lm.cvmaker.model.User;
import com.lm.cvmaker.persistence.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CvService {

    private final CvRepository cvRepository;
    private final ProjectRepository projectRepository;
    private final ExperienceRepository experienceRepository;
    private final EducationRepository educationRepository;
    private final HugginFaceService hugginFaceService;
    private final UserRepository userRepository;

    public CvService(CvRepository cvRepository, ProjectRepository projectRepository, ExperienceRepository experienceRepository, EducationRepository educationRepository, HugginFaceService hugginFaceService, UserRepository userRepository) {
        this.cvRepository = cvRepository;
        this.projectRepository = projectRepository;
        this.experienceRepository = experienceRepository;
        this.educationRepository = educationRepository;
        this.hugginFaceService = hugginFaceService;
        this.userRepository = userRepository;
    }

    public Cv createCv(Cv cv) {
        String generatedSummary = hugginFaceService.generateSummary(cv);
        cv.setSummary(generatedSummary);
        Cv savedCv = cvRepository.save(cv);

        cv.getExperiences().forEach(exp -> {
            exp.setCv(savedCv);
            experienceRepository.save(exp);
        });

        cv.getEducation().forEach(edu -> {
            edu.setCv(savedCv);
            educationRepository.save(edu);
        });

        cv.getProjects().forEach(project -> {
            project.setCv(savedCv);
            projectRepository.save(project);
        });

        return savedCv;
    }

    public Cv generateAndSaveCv(Long userId, CvRequest request) {
        System.out.println("Searching for user with ID: " + userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Cv cv = new Cv();

        cv.setKeywords(request.getKeywords());
        cv.setEducation(request.getEducation());
        cv.setProjects(request.getProjects());

        List<Experience> experiences = request.getExperiences().stream()
                .map(exp -> new Experience(exp.getJobTitle(), exp.getCompany(), exp.getStartYear(), exp.getEndYear(), exp.getKeywords()))
                .collect(Collectors.toList());
        cv.setExperiences(experiences);

        for (Experience experience : experiences) {
            String jobSummary = hugginFaceService.generateExperienceSummary(experience);
            experience.setDescription(jobSummary);
        }

        String generatedText = hugginFaceService.generateSummary(cv);
        cv.setGeneratedText(generatedText);
        return cvRepository.save(cv);

    }

    public Cv getUserCvs(Long id) {
        Cv cv = cvRepository.findByIdBasic(id)
                .orElseThrow(() -> new RuntimeException("CV not found"));

        // Manually load experiences & education
        cv.setExperiences(experienceRepository.findByCvId(id));
        cv.setEducation(educationRepository.findByCvId(id));
        return cv;
    }

    public List<Cv> getAllCvs() {
        return cvRepository.findAll();
    }

    public void deleteCv(Long id) {
        cvRepository.deleteById(id);
    }

}
