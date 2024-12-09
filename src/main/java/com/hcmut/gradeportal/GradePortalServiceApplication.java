package com.hcmut.gradeportal;

import java.io.File;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class GradePortalServiceApplication {

    public static void main(String[] args) {

        String envPath = ".env";
        File envFile = new File(envPath);

        // Kiểm tra nếu file tồn tại
        if (envFile.exists() && envFile.isFile()) {
            // Tải biến từ file .env
            Dotenv dotenv = Dotenv.load();

            // Thiết lập các biến môi trường cho Spring Boot
            System.setProperty("SPRING_APPLICATION_NAME", dotenv.get("SPRING_APPLICATION_NAME"));
            System.setProperty("DB_URL", dotenv.get("DB_URL"));
            System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
            System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));
            System.setProperty("DB_DRIVER", dotenv.get("DB_DRIVER"));
            System.setProperty("SERVER_PORT", dotenv.get("SERVER_PORT"));
            System.setProperty("SERVER_CONTEXT_PATH", dotenv.get("SERVER_CONTEXT_PATH"));
            System.setProperty("GOOGLE_CLIENT_ID", dotenv.get("GOOGLE_CLIENT_ID"));
            System.setProperty("GOOGLE_CLIENT_SECRET", dotenv.get("GOOGLE_CLIENT_SECRET"));
            System.setProperty("GOOGLE_REDIRECT_URI", dotenv.get("GOOGLE_REDIRECT_URI"));
            System.setProperty("GOOGLE_AUTH_URI", dotenv.get("GOOGLE_AUTH_URI"));
            System.setProperty("GOOGLE_TOKEN_URI", dotenv.get("GOOGLE_TOKEN_URI"));
            System.setProperty("GOOGLE_USER_INFO_URI", dotenv.get("GOOGLE_USER_INFO_URI"));
            System.setProperty("GOOGLE_USER_NAME_ATTRIBUTE", dotenv.get("GOOGLE_USER_NAME_ATTRIBUTE"));
            System.setProperty("JACKSON_TIME_ZONE", dotenv.get("JACKSON_TIME_ZONE"));
        }
        SpringApplication.run(GradePortalServiceApplication.class, args);
    }
}
