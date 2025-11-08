package com.example.demo.service;


import com.example.demo.model.SimplifiedTerm;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class MedicalTermService {

    private final Map<String, String[]> medicalDictionary = new HashMap<>();
    private final ApiService apiService;

    public MedicalTermService(ApiService apiService) {
        this.apiService = apiService;
        initializeDictionary();
    }

    private void initializeDictionary() {
        medicalDictionary.put("tachycardia", new String[]{
                "A heart rate that's too fast, usually over 100 beats per minute at rest.",
                "It can make your heart work less efficiently and may lead to more serious conditions if untreated."
        });
        medicalDictionary.put("ejection fraction", new String[]{
                "A measurement of how much blood the left ventricle pumps out with each contraction.",
                "It's a key indicator of your heart's pumping strength and is used to diagnose heart failure."
        });
        medicalDictionary.put("atrial fibrillation", new String[]{
                "An irregular and often rapid heart rhythm that can lead to blood clots, stroke, and other heart-related complications.",
                "It increases your risk of stroke and heart failure, and needs proper management."
        });
        medicalDictionary.put("edema", new String[]{
                "Swelling caused by excess fluid trapped in your body's tissues.",
                "It can indicate heart failure, kidney disease, or circulatory problems."
        });
        medicalDictionary.put("cardiomyopathy", new String[]{
                "A disease of the heart muscle that makes it harder for your heart to pump blood to the rest of your body.",
                "It can lead to heart failure and requires ongoing medical care."
        });
    }

    public SimplifiedTerm lookupTerm(String term) {
        String lowerTerm = term.toLowerCase();
        if (medicalDictionary.containsKey(lowerTerm)) {
            String[] details = medicalDictionary.get(lowerTerm);
            SimplifiedTerm simplifiedTerm = new SimplifiedTerm();
            simplifiedTerm.setTerm(term);
            simplifiedTerm.setMeaning(details[0]);
            simplifiedTerm.setWhyItMatters(details[1]);
            return simplifiedTerm;
        }
        return null;
    }

    public SimplifiedTerm findAndTranslateTerm(String term) {
        // 1. First, check the local dictionary
        SimplifiedTerm result = lookupTerm(term);

        // 2. If not found, call the AI API
        if (result == null) {
            result = apiService.lookupTermWithAI(term);
        }
        return result;
    }
}