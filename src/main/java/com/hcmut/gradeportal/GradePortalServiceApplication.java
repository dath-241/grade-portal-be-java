package com.hcmut.gradeportal;

// import java.io.File;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class GradePortalServiceApplication {

    public static void main(String[] args) {
        // Khởi chạy Spring Boot
        SpringApplication app = new SpringApplication(GradePortalServiceApplication.class);
        app.run(args);
    }
}
