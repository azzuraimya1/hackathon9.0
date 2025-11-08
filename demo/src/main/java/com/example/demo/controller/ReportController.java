package com.example.demo.controller;


import com.example.demo.model.SimplifiedTerm;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import com.example.demo.service.ReportProcessingService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportProcessingService reportProcessingService;
    private final UserService userService;

    public ReportController(ReportProcessingService reportProcessingService, UserService userService) {
        this.reportProcessingService = reportProcessingService;
        this.userService = userService;
    }

    // Your existing process endpoint
    @PostMapping("/process")
    public ResponseEntity<?> processMedicalText(@RequestBody ProcessRequest request,
                                                @AuthenticationPrincipal org.springframework.security.core.userdetails.User securityUser) {
        try {
            User user = userService.findByUsername(securityUser.getUsername());
            List<SimplifiedTerm> simplifiedTerms = reportProcessingService.processReport(request.getMedicalText());

            ProcessResponse response = new ProcessResponse(
                    "Report processed successfully",
                    simplifiedTerms.size(),
                    simplifiedTerms
            );

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    new ErrorResponse("Processing failed: " + e.getMessage())
            );
        }
    }

    // NEW: File upload endpoint
    @PostMapping("/upload")
    public ResponseEntity<?> uploadMedicalFile(@RequestParam("file") MultipartFile file,
                                               @AuthenticationPrincipal org.springframework.security.core.userdetails.User securityUser) {
        try {
            // Check if file is empty
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body(new ErrorResponse("Please select a file to upload"));
            }

            // Check file type
            String contentType = file.getContentType();
            if (!isSupportedContentType(contentType)) {
                return ResponseEntity.badRequest().body(new ErrorResponse("Please upload a PDF or text file"));
            }

            // Get user
            User user = userService.findByUsername(securityUser.getUsername());

            // Process file based on type
            String fileContent;
            if (contentType != null && contentType.equals("application/pdf")) {
                // For PDF files - simple text extraction (mock for demo)
                fileContent = "Mock PDF extraction: This would contain text from PDF file. Patient has tachycardia and edema.";
                // In real implementation, use Apache PDFBox or similar
            } else {
                // For text files
                fileContent = new String(file.getBytes());
            }

            // Process the content
            List<SimplifiedTerm> simplifiedTerms = reportProcessingService.processReport(fileContent);

            // Create response
            ProcessResponse response = new ProcessResponse(
                    "File processed successfully: " + file.getOriginalFilename(),
                    simplifiedTerms.size(),
                    simplifiedTerms
            );

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    new ErrorResponse("File upload failed: " + e.getMessage())
            );
        }
    }

    private boolean isSupportedContentType(String contentType) {
        return contentType != null && (
                contentType.equals("text/plain") ||
                        contentType.equals("application/pdf") ||
                        contentType.equals("text/html")
        );
    }

    // Request/Response classes (make sure these are in your controller)
    public static class ProcessRequest {
        private String medicalText;

        public String getMedicalText() { return medicalText; }
        public void setMedicalText(String medicalText) { this.medicalText = medicalText; }
    }

    public static class ProcessResponse {
        private String message;
        private int termsFound;
        private List<SimplifiedTerm> simplifiedTerms;

        public ProcessResponse(String message, int termsFound, List<SimplifiedTerm> simplifiedTerms) {
            this.message = message;
            this.termsFound = termsFound;
            this.simplifiedTerms = simplifiedTerms;
        }

        public String getMessage() { return message; }
        public int getTermsFound() { return termsFound; }
        public List<SimplifiedTerm> getSimplifiedTerms() { return simplifiedTerms; }
    }

    public static class ErrorResponse {
        private String error;

        public ErrorResponse(String error) {
            this.error = error;
        }

        public String getError() { return error; }
        public void setError(String error) { this.error = error; }
    }
}