package com.hcmut.gradeportal.controller.health_check;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.jdbc.core.JdbcTemplate;

@RestController
@RequestMapping("/health_check")
public class HealthCheckController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping
    public ResponseEntity<String> healthCheck() {
        try {
            // Kiểm tra kết nối database
            jdbcTemplate.execute("SELECT 1");
            return ResponseEntity.ok("System is healthy");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Database connection failed");
        }
    }
}