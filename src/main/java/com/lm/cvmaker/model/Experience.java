package com.lm.cvmaker.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Experience {

    public Experience(String jobTitle, String company, int startYear, int endYear, List<String> keywords) {
        this.jobTitle = jobTitle;
        this.company = company;
        this.startYear = startYear;
        this.endYear = endYear;
        this.keywords = keywords;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String company;
    private String jobTitle;
    private int startYear;
    private int endYear;
    @Lob
    private String description;
    @ElementCollection
    private List<String> keywords;

    @ManyToOne
    @JoinColumn(name = "cv_id")
    @JsonBackReference
    private Cv cv;



}
