package com.example.demo.controller;


import com.example.demo.model.SimplifiedTerm;
import com.example.demo.model.User;
import com.example.demo.service.ReportProcessingService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportProcessingService reportProcessingService;

    public ReportController(ReportProcessingService reportProcessingService) {
        this.reportProcessingService = reportProcessingService;
    }

    @PostMapping("/process")
    public List<SimplifiedTerm> processMedicalText(@RequestBody String medicalText, @AuthenticationPrincipal User user) {
        // 1. Process the text to find and translate terms
        List<SimplifiedTerm> simplifiedTerms = reportProcessingService.processReport(medicalText);

        // 2. (Optional) Save the report and terms to the database for history
        // MedicalReport report = new MedicalReport();
        // report.setOriginalText(medicalText);
        // report.setUser(user);
        // report.setUploadDate(LocalDateTime.now());
        // report.setSimplifiedTerms(simplifiedTerms);
        // medicalReportRepository.save(report); // You'd need a Repository for this

        // 3. Return the structured output to the frontend
        return simplifiedTerms;
    }
}
