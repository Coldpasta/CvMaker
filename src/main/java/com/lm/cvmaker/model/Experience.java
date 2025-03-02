package com.lm.cvmaker.model;

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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String company;
    private String jobTitle;
    private int startYear;
    private int endYear;
    private String description;
    private List<String> keywords;

    @ManyToOne
    @JoinColumn(name = "cv_id")
    private Cv cv;


}
