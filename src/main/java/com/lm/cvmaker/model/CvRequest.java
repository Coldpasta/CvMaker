package com.lm.cvmaker.model;

import lombok.Data;

import java.util.List;

@Data
public class CvRequest {
    private String keywords;
    private List<Education> education;
    private List<Project> projects;
    private List<Experience> experiences;  
}

