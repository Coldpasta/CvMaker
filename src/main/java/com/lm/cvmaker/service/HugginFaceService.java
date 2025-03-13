package com.lm.cvmaker.service;

import com.lm.cvmaker.model.Cv;
import com.lm.cvmaker.model.Experience;
import com.lm.cvmaker.model.User;
import com.lm.cvmaker.persistence.CvRepository;
import com.lm.cvmaker.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class HugginFaceService {

    @Value("${huggingface.api.key}")
    private String apiKey;

    @Value("${huggingface.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate = new RestTemplate();
    private final UserRepository userRepository;
    private final CvRepository cvRepository;

    public HugginFaceService(CvRepository cvRepository, UserRepository userRepository) {
        this.userRepository = userRepository;
        this.cvRepository = cvRepository;
    }


    public Cv generateAndSaveCv(Long userId, String keywords) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.set("Content-Type", "application/json");

        // Create a prompt sentence using the keywords
        String prompt = "Write a CV paragraph containing of bullet points that would be fitted for a position that was using these skills: " + keywords;

        Map<String, Object> requestBody = Collections.singletonMap("inputs", prompt);
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, request, String.class);
        String generatedText = response.getBody();

        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            Cv cv = new Cv(keywords, generatedText, user);
            return cvRepository.save(cv);
        }
        throw new RuntimeException("User not found");

    }

    public String generateProfessionalTitle(Cv cv) {
        RestTemplate restTemplate = new RestTemplate();

        // Creates a professional title to be put as the title next to the candidate's name
        String prompt = "Generate a single professional title for a candidate with these professional experiences :\n";
        for (Experience exp : cv.getExperiences())
            prompt += "- " + exp.getJobTitle() + " at " + exp.getCompany() +
                    " (" + exp.getStartYear() + "-" + exp.getEndYear() + " years). Keywords: " +
                    String.join(", ", exp.getKeywords()) + ".\n";


        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("inputs", prompt);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, entity, String.class);

        return response.getBody();
    }
    public String generateExperienceSummary(Experience experience) {
        RestTemplate restTemplate = new RestTemplate();

        // Create prompt for a single job experience
        String prompt = "Generate a paragraph with bullet points with description of main responsibilities for a job role: " +
                experience.getJobTitle() + " at " + experience.getCompany() +
                ". Keywords: " + String.join(", ", experience.getKeywords()) +
                ". Work experience: " + experience.getStartYear() + "-" + experience.getEndYear() + " years.";

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("inputs", prompt);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                apiUrl, HttpMethod.POST, entity, String.class);

        return response.getBody();
    }


    public Optional<Cv> getUserCvs(Long userId) {
        return cvRepository.findByIdBasic(userId);
    }
}