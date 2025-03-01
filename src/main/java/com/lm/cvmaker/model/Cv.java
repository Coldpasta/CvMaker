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
public class Cv {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String keywords;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String generatedText;

    private String fullName;
    private String email;
    private String phone;
    private String summary;

    @ElementCollection
    private List<String> skills;

    @OneToMany(mappedBy = "cv", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Experience> experiences;
    @OneToMany(mappedBy = "cv", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Education> education;
    @OneToMany(mappedBy = "cv", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Project> projects;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Cv(String keywords, String generatedText, User user) {
        this.keywords = keywords;
        this.generatedText = generatedText;
        this.user = user;

    }


}



