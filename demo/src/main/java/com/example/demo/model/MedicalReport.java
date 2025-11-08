package com.example.demo.model;


import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class MedicalReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String originalText;

    private LocalDateTime uploadDate;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "report", cascade = CascadeType.ALL)
    private List<SimplifiedTerm> simplifiedTerms;

    // Constructors
    public MedicalReport() {}
    public MedicalReport(String originalText, User user) {
        this.originalText = originalText;
        this.user = user;
        this.uploadDate = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getOriginalText() { return originalText; }
    public void setOriginalText(String originalText) { this.originalText = originalText; }
    public LocalDateTime getUploadDate() { return uploadDate; }
    public void setUploadDate(LocalDateTime uploadDate) { this.uploadDate = uploadDate; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public List<SimplifiedTerm> getSimplifiedTerms() { return simplifiedTerms; }
    public void setSimplifiedTerms(List<SimplifiedTerm> simplifiedTerms) { this.simplifiedTerms = simplifiedTerms; }
}