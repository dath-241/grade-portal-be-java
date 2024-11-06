package com.hcmut.gradeportal.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.util.Arrays;

@Component
public class ConfigChecker {

    @Autowired
    private Environment env;

    @PostConstruct
    public void checkConfigValues() {
        System.out.println("Application Name: " + env.getProperty("spring.application.name"));
        System.out.println("Server Port: " + env.getProperty("server.port"));
        System.out.println("Server Context Path: " + env.getProperty("server.servlet.context-path"));

        System.out.println("Database URL: " + env.getProperty("spring.datasource.url"));
        System.out.println("Database Username: " + env.getProperty("spring.datasource.username"));
        System.out.println("Database Password: " + env.getProperty("spring.datasource.password"));
        System.out.println("Database Driver: " + env.getProperty("spring.datasource.driver-class-name"));

        System.out.println(
                "Google Client ID: " + env.getProperty("spring.security.oauth2.client.registration.google.client-id"));
        System.out.println("Google Client Secret: "
                + env.getProperty("spring.security.oauth2.client.registration.google.client-secret"));
        System.out.println("Google Redirect URI: "
                + env.getProperty("spring.security.oauth2.client.registration.google.redirect-uri"));

        System.out.println("JWT Secret Key: " + env.getProperty("jwt.secret-key"));
        System.out.println("JWT Expiration Time: " + env.getProperty("jwt.expiration-time"));

        System.out.println("Jackson Time Zone: " + env.getProperty("spring.jackson.time-zone"));

        // Optional: List all properties in environment
        Arrays.stream(env.getActiveProfiles()).forEach(profile -> System.out.println("Active profile: " + profile));
    }
}
