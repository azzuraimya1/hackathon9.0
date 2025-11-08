package com.example.demo;


import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ApiTest implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        System.out.println("=== MEDICAL APP API TEST ===");
        System.out.println("Server running on: http://localhost:8080");
        System.out.println("H2 Console: http://localhost:8080/h2-console");
        System.out.println("Test endpoints:");
        System.out.println("  POST http://localhost:8080/api/auth/register");
        System.out.println("  POST http://localhost:8080/api/auth/login");
        System.out.println("  POST http://localhost:8080/api/reports/process");
        System.out.println("=================================");
    }
}
