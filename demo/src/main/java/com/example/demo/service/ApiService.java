package com.example.demo.service;


import com.example.demo.model.SimplifiedTerm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ApiService {

    @Value("${openai.api.key:demo-key}") // Default value for demo
    private String apiKey;

    public SimplifiedTerm lookupTermWithAI(String term) {
        // For hackathon demo, we'll mock the AI response
        // In a real implementation, you'd call OpenAI API or similar

        SimplifiedTerm aiTerm = new SimplifiedTerm();
        aiTerm.setTerm(term);
        aiTerm.setMeaning("This is a simulated AI-generated explanation for " + term + ". In a full implementation, this would come from a real AI API like OpenAI.");
        aiTerm.setWhyItMatters("This simulated explanation shows why " + term + " is important for your heart health. A real AI would provide more detailed, accurate information.");

        return aiTerm;
    }
}