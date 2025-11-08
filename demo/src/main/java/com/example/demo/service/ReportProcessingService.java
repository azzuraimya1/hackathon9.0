package com.example.demo.service;


import com.example.demo.model.SimplifiedTerm;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReportProcessingService {

    private final MedicalTermService medicalTermService;
    private final List<String> commonComplexTerms;

    public ReportProcessingService(MedicalTermService medicalTermService) {
        this.medicalTermService = medicalTermService;
        this.commonComplexTerms = List.of(
                "tachycardia", "ejection fraction", "atrial fibrillation",
                "edema", "cardiomyopathy", "arrhythmia", "myocardial infarction",
                "heart failure", "hypertension", "hypotension", "ischemia"
        );
    }

    public List<SimplifiedTerm> processReport(String originalText) {
        List<SimplifiedTerm> results = new ArrayList<>();

        // Simple word-by-word processing for hackathon
        String[] words = originalText.split("\\W+");

        for (String word : words) {
            if (word.length() > 5 && commonComplexTerms.contains(word.toLowerCase())) {
                SimplifiedTerm simplified = medicalTermService.findAndTranslateTerm(word);
                if (simplified != null) {
                    results.add(simplified);
                }
            }
        }
        return results;
    }
}