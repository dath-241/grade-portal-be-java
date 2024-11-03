package com.hcmut.gradeportal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class GradePortalServiceApplication {

    public static void main(String[] args) {

        // Tải biến từ file .env
        Dotenv dotenv = Dotenv.load();

        // Thiết lập các biến môi trường cho Spring Boot
        // General_Configuration
        System.setProperty("SPRING_APPLICATION_NAME", dotenv.get("SPRING_APPLICATION_NAME"));

        // Server_Configuration
        System.setProperty("SERVER_PORT", dotenv.get("SERVER_PORT"));
        System.setProperty("SERVER_CONTEXT_PATH", dotenv.get("SERVER_CONTEXT_PATH"));

        // Database_Configuration
        System.setProperty("DB_URL", dotenv.get("DB_URL"));
        System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
        System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));
        System.setProperty("DB_DRIVER", dotenv.get("DB_DRIVER"));

        // Oauth2_Configuration
        System.setProperty("GOOGLE_CLIENT_ID", dotenv.get("GOOGLE_CLIENT_ID"));
        System.setProperty("GOOGLE_CLIENT_SECRET", dotenv.get("GOOGLE_CLIENT_SECRET"));
        System.setProperty("GOOGLE_REDIRECT_URI", dotenv.get("GOOGLE_REDIRECT_URI"));

        // Jwt_Configuration
        System.setProperty("JWT_SECRET_KEY", dotenv.get("JWT_SECRET_KEY"));
        System.setProperty("JWT_EXPIRATION_TIME", dotenv.get("JWT_EXPIRATION_TIME"));

        // Time_Zone_Configuration
        System.setProperty("JACKSON_TIME_ZONE", dotenv.get("JACKSON_TIME_ZONE"));

        SpringApplication.run(GradePortalServiceApplication.class, args);
    }

}
