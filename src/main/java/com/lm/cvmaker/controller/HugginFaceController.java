package com.lm.cvmaker.controller;

import com.lm.cvmaker.model.Cv;
import com.lm.cvmaker.service.HugginFaceService;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/api/cv")
public class HugginFaceController {


    private final HugginFaceService hugginFaceService;

    public HugginFaceController(HugginFaceService hugginFaceService) {
        this.hugginFaceService = hugginFaceService;
    }

    @PostMapping("/generate/{userId}")
    public Cv generateAndSaveCv(@PathVariable Long userId, @RequestBody String keywords) {
        return hugginFaceService.generateAndSaveCv(userId, keywords);
    }
    @GetMapping("/generate/{userId}")
    public Optional<Cv> getUserCvs(@PathVariable Long userId){
        return hugginFaceService.getUserCvs(userId);
    }
}