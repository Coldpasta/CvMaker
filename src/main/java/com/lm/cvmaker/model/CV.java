package com.lm.cvmaker.model;

import jakarta.persistence.*;
import org.w3c.dom.Text;

@Entity
public class CV {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String keywords;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String generatedText;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
    public CV(){}

    public CV(String keywords, String generatedText, User user) {
        this.keywords = keywords;
        this.generatedText = generatedText;
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public String getKeywords() {
        return keywords;
    }

    public String getGeneratedText() {
        return generatedText;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
