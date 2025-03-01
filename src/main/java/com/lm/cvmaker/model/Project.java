package com.lm.cvmaker.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String projectName;
    private String description;
    private String link;

    @ManyToOne
    @JoinColumn(name="cv_id")
    private Cv cv;

}

