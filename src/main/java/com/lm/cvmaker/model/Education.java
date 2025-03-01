package com.lm.cvmaker.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Education {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String degree;
    private String university;
    private int graduationYear;

    @ManyToOne
    @JoinColumn(name = "cv_id")
    private Cv cv;


}
