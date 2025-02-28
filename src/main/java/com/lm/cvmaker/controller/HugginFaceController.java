package com.lm.cvmaker.controller;

import com.lm.cvmaker.model.CV;
import com.lm.cvmaker.service.HugginFaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cv")
public class HugginFaceController {

    @Autowired
    private HugginFaceService hugginFaceService;

    @PostMapping("/generate/{userId}")
    public CV generateAndSaveCv(@PathVariable Long userId, @RequestBody String keywords) {
        return hugginFaceService.generateAndSaveCv(userId, keywords);
    }
    @GetMapping("/generate/{userId}")
    public List<CV> getUserCvs(@PathVariable Long userId){
        return hugginFaceService.getUserCvs(userId);
    }
}