package com.lm.cvmaker.service;

import com.lm.cvmaker.model.Cv;
import com.lm.cvmaker.model.CvRequest;
import com.lm.cvmaker.model.Experience;
import com.lm.cvmaker.model.User;
import com.lm.cvmaker.persistence.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CvService {

    private final CvRepository cvRepository;
    private final ExperienceRepository experienceRepository;
    private final EducationRepository educationRepository;
    private final HugginFaceService hugginFaceService;
    private final UserRepository userRepository;

    public CvService(CvRepository cvRepository, ExperienceRepository experienceRepository, EducationRepository educationRepository, HugginFaceService hugginFaceService, UserRepository userRepository) {
        this.cvRepository = cvRepository;
        this.experienceRepository = experienceRepository;
        this.educationRepository = educationRepository;
        this.hugginFaceService = hugginFaceService;
        this.userRepository = userRepository;
    }

    public Cv generateAndSaveCv(Long userId, CvRequest request) {
        System.out.println("Searching for user with ID: " + userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Cv cv = new Cv();

        cv.setKeywords(request.getKeywords());
        cv.setEducation(request.getEducation());
        cv.setProjects(request.getProjects());
        cv.setUser(user);
        cv.setFullName(user.getName());
        cv.setEmail(user.getEmail());

        List<Experience> experiences = request.getExperiences().stream()
                .map(exp -> new Experience(exp.getJobTitle(), exp.getCompany(), exp.getStartYear(), exp.getEndYear(), exp.getKeywords()))
                .collect(Collectors.toList());
        cv.setExperiences(experiences);

        for (Experience experience : experiences) {
            String jobSummary = hugginFaceService.generateExperienceSummary(experience);
            experience.setDescription(jobSummary);
        }

        String generatedText = hugginFaceService.generateProfessionalTitle(cv);
        cv.setProfessionalTitle(generatedText);
        return cvRepository.save(cv);

    }

    public Cv getUserCvs(Long id) {
        Cv cv = cvRepository.findByIdBasic(id)
                .orElseThrow(() -> new RuntimeException("CV not found"));
        cv.setExperiences(experienceRepository.findByCvId(id));
        cv.setEducation(educationRepository.findByCvId(id));
        return cv;
    }

    public List<Cv> getAllCvs() {
        return cvRepository.findAll();
    }

    @Transactional
    public Optional<Cv> deleteCv(Long id) {
        Optional<Cv> cvOptional = cvRepository.findByIdBasic(id);
        if (cvOptional.isEmpty()) {
            throw new IllegalArgumentException("Cv with id" + id + "was not found");
        } else
            cvRepository.deleteById(id);
        return cvRepository.findByIdBasic(id);
    }


}


