package com.hcmut.gradeportal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class GradePortalServiceApplication {

    public static void main(String[] args) {

        // // Tải biến từ file .env
        // Dotenv dotenv = Dotenv.load();

        // // Thiết lập các biến môi trường cho Spring Boot
        // // General_Configuration
        // System.setProperty("SPRING_APPLICATION_NAME",
        // dotenv.get("SPRING_APPLICATION_NAME"));

        // // Server_Configuration
        // System.setProperty("SERVER_PORT", dotenv.get("SERVER_PORT"));
        // System.setProperty("SERVER_CONTEXT_PATH", dotenv.get("SERVER_CONTEXT_PATH"));

        // // Database_Configuration
        // System.setProperty("DB_URL", dotenv.get("DB_URL"));
        // System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
        // System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));
        // System.setProperty("DB_DRIVER", dotenv.get("DB_DRIVER"));

        // // Oauth2_Configuration
        // System.setProperty("GOOGLE_CLIENT_ID", dotenv.get("GOOGLE_CLIENT_ID"));
        // System.setProperty("GOOGLE_CLIENT_SECRET",
        // dotenv.get("GOOGLE_CLIENT_SECRET"));
        // System.setProperty("GOOGLE_REDIRECT_URI", dotenv.get("GOOGLE_REDIRECT_URI"));
        // System.out.println("GOOGLE_CLIENT_ID: " + dotenv.get("GOOGLE_CLIENT_ID"));

        // // Jwt_Configuration
        // System.setProperty("JWT_SECRET_KEY", dotenv.get("JWT_SECRET_KEY"));
        // System.setProperty("JWT_EXPIRATION_TIME", dotenv.get("JWT_EXPIRATION_TIME"));

        // // Time_Zone_Configuration
        // System.setProperty("JACKSON_TIME_ZONE", dotenv.get("JACKSON_TIME_ZONE"));

        // Xóa các biến môi trường hiện có trước khi thiết lập lại
        clearEnvironmentVariables();

        Dotenv dotenv = Dotenv.load();

        dotenv.entries().forEach(entry -> {
            // Kiểm tra biến trong System properties và ghi đè nếu đã tồn tại
            if (System.getProperty(entry.getKey()) != null) {
                System.clearProperty(entry.getKey()); // Xóa biến nếu đã tồn tại
            }
            System.setProperty(entry.getKey(), entry.getValue()); // Thiết lập lại từ .env
        });

        // Kiểm tra giá trị sau khi ghi đè (ví dụ cho các biến quan trọng)
        System.out.println("SPRING_APPLICATION_NAME: " + System.getProperty("SPRING_APPLICATION_NAME"));
        System.out.println("SERVER_PORT: " + System.getProperty("SERVER_PORT"));
        System.out.println("DB_URL: " + System.getProperty("DB_URL"));
        System.out.println("GOOGLE_CLIENT_ID: " + System.getProperty("GOOGLE_CLIENT_ID"));
        System.out.println("JWT_SECRET_KEY: " + System.getProperty("JWT_SECRET_KEY"));

        SpringApplication.run(GradePortalServiceApplication.class, args);
    }

    private static void clearEnvironmentVariables() {
        System.clearProperty("SPRING_APPLICATION_NAME");
        System.clearProperty("SERVER_PORT");
        System.clearProperty("SERVER_CONTEXT_PATH");
        System.clearProperty("DB_URL");
        System.clearProperty("DB_USERNAME");
        System.clearProperty("DB_PASSWORD");
        System.clearProperty("DB_DRIVER");
        System.clearProperty("GOOGLE_CLIENT_ID");
        System.clearProperty("GOOGLE_CLIENT_SECRET");
        System.clearProperty("GOOGLE_REDIRECT_URI");
        System.clearProperty("JWT_SECRET_KEY");
        System.clearProperty("JWT_EXPIRATION_TIME");
        System.clearProperty("JACKSON_TIME_ZONE");
    }

}
