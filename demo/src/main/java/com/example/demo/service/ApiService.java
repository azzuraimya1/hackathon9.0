package com.example.demo.service;


import com.example.demo.model.SimplifiedTerm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class ApiService {

    @Value("${openai.api.key:demo-key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    public ApiService() {
        this.restTemplate = new RestTemplate();
    }

    public SimplifiedTerm lookupTermWithAI(String term) {
        // If no real API key, use mock for demo
        if (apiKey.equals("demo-key") || apiKey.isEmpty()) {
            return createMockAITerm(term);
        }

        try {
            String url = "https://api.openai.com/v1/chat/completions";

            // Create headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(apiKey);

            // Create the request body
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("model", "gpt-3.5-turbo");

            Map<String, String> systemMessage = new HashMap<>();
            systemMessage.put("role", "system");
            systemMessage.put("content", "You are a medical translator for patients. Explain medical terms in simple, easy-to-understand language. Always respond with exactly two sentences: first sentence explains what it is, second sentence explains why it matters to the patient.");

            Map<String, String> userMessage = new HashMap<>();
            userMessage.put("role", "user");
            userMessage.put("content", "Explain this medical term: " + term);

            requestBody.put("messages", new Object[]{systemMessage, userMessage});
            requestBody.put("max_tokens", 150);
            requestBody.put("temperature", 0.7);

            // Create the request entity
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

            // Make the API call
            ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, entity, Map.class);

            // Parse the response
            Map<String, Object> responseBody = response.getBody();
            if (responseBody != null && responseBody.containsKey("choices")) {
                Map<String, Object> choice = ((java.util.List<Map<String, Object>>) responseBody.get("choices")).get(0);
                Map<String, Object> message = (Map<String, Object>) choice.get("message");
                String content = (String) message.get("content");

                return parseAIResponse(term, content);
            }

        } catch (Exception e) {
            System.out.println("OpenAI API call failed: " + e.getMessage());
            // Fall back to mock response
        }

        return createMockAITerm(term);
    }

    private SimplifiedTerm parseAIResponse(String term, String content) {
        SimplifiedTerm aiTerm = new SimplifiedTerm();
        aiTerm.setTerm(term);

        // Simple parsing - split by sentences
        String[] sentences = content.split("\\. ");
        if (sentences.length >= 2) {
            aiTerm.setMeaning(sentences[0] + ".");
            aiTerm.setWhyItMatters(sentences[1] + (sentences[1].endsWith(".") ? "" : "."));
        } else {
            // Fallback if parsing fails
            aiTerm.setMeaning("AI Explanation: " + content);
            aiTerm.setWhyItMatters("This information helps you understand your health condition better.");
        }

        return aiTerm;
    }

    private SimplifiedTerm createMockAITerm(String term) {
        SimplifiedTerm aiTerm = new SimplifiedTerm();
        aiTerm.setTerm(term);
        aiTerm.setMeaning("AI Explanation: " + term + " is a medical condition that affects heart function.");
        aiTerm.setWhyItMatters("Understanding " + term + " helps you manage your heart health and communicate better with your doctor.");
        return aiTerm;
    }


}