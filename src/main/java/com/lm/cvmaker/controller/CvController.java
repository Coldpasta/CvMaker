package com.lm.cvmaker.controller;

import com.lm.cvmaker.model.Cv;
import com.lm.cvmaker.model.CvRequest;
import com.lm.cvmaker.service.CvService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cv")
public class CvController {

    private final CvService cvService;


    public CvController(CvService cvService) {
        this.cvService = cvService;
    }

    @PostMapping("/generate")
    public ResponseEntity<Cv> generateCv(@RequestParam Long userId,
                                         @RequestBody CvRequest request) {
        Cv generatedCv = cvService.generateAndSaveCv(userId, request);
        return ResponseEntity.ok(generatedCv);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Cv> getUserCvs(@PathVariable Long userId) {
        Cv cvs = cvService.getUserCvs(userId);
        return ResponseEntity.ok(cvs);
    }

    @GetMapping
    public List<Cv> getAllCvs() {
        return cvService.getAllCvs();
    }


    @DeleteMapping("/{cvId}")
    public ResponseEntity<?> deleteCv(@PathVariable Long cvId) {
        try {
            return ResponseEntity.ok(cvService.deleteCv(cvId));

        } catch (IllegalArgumentException e) {

            return ResponseEntity.badRequest().body("Cv with id " + cvId + " was not found");
        }
    }
}
