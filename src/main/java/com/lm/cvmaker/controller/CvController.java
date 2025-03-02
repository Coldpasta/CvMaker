package com.lm.cvmaker.controller;

import com.lm.cvmaker.model.Cv;
import com.lm.cvmaker.service.CvService;
import com.lm.cvmaker.service.HugginFaceService;
import com.lm.cvmaker.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cv")
public class CvController {

    private final CvService cvService;
    private final UserService userService;
    private final HugginFaceService hugginFaceService;

    public CvController(CvService cvService, UserService userService, HugginFaceService hugginFaceService) {
        this.cvService = cvService;
        this.userService = userService;
        this.hugginFaceService = hugginFaceService;

    }

    @PostMapping("/generate")
    public ResponseEntity<Cv> generateCv(@RequestParam Long userId, @RequestParam String keywords) {
        Cv generatedCv = cvService.generateAndSaveCv(userId, keywords);
        return ResponseEntity.ok(generatedCv);
    }
    @GetMapping("/{userId}")
    public ResponseEntity<List<Cv>> getUserCvs(@PathVariable Long userId){
        List<Cv> cvs = cvService.getUserCvs(userId);
        return ResponseEntity.ok(cvs);
    }

}
