package com.example.demo.model;


import jakarta.persistence.*;

@Entity
public class SimplifiedTerm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String term;

    @Lob
    private String meaning;

    @Lob
    private String whyItMatters;

    @ManyToOne
    @JoinColumn(name = "report_id", nullable = false)
    private MedicalReport report;

    // Constructors
    public SimplifiedTerm() {}
    public SimplifiedTerm(String term, String meaning, String whyItMatters, MedicalReport report) {
        this.term = term;
        this.meaning = meaning;
        this.whyItMatters = whyItMatters;
        this.report = report;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTerm() { return term; }
    public void setTerm(String term) { this.term = term; }
    public String getMeaning() { return meaning; }
    public void setMeaning(String meaning) { this.meaning = meaning; }
    public String getWhyItMatters() { return whyItMatters; }
    public void setWhyItMatters(String whyItMatters) { this.whyItMatters = whyItMatters; }
    public MedicalReport getReport() { return report; }
    public void setReport(MedicalReport report) { this.report = report; }
}