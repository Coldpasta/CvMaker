package com.lm.cvmaker.service;

import com.lm.cvmaker.model.CV;
import com.lm.cvmaker.model.User;
import com.lm.cvmaker.persistence.CvRepository;
import com.lm.cvmaker.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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


    public CV generateAndSaveCv(Long userId, String keywords) {
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
            CV cv = new CV(keywords, generatedText, user);
            return cvRepository.save(cv);
        }
        throw new RuntimeException("User not found");

    }

    public List<CV> getUserCvs(Long userId) {
        return cvRepository.findByUserId(userId);
    }
}