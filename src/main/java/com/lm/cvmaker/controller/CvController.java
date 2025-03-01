package com.lm.cvmaker.controller;

import com.lm.cvmaker.model.Cv;
import com.lm.cvmaker.service.CvService;
import com.lm.cvmaker.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cv")
public class CvController {

    private final CvService cvService;
    private final UserService userService;

    public CvController(CvService cvService, UserService userService) {
        this.cvService = cvService;
        this.userService = userService;
    }

    @PostMapping("/generate")
    public ResponseEntity<Cv> generateCv (@RequestParam Long id, @RequestParam String keywords){
        Cv cv = cvService.generateandSave(cv);
    }
}
