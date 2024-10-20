package com.hcmut.gradeportal.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.Table;
import lombok.Data;
import jakarta.persistence.InheritanceType;

import java.time.LocalDateTime;
import java.util.UUID;

import com.hcmut.gradeportal.entities.enums.Role;

@Data
@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
public class User {

    @Id
    private String id;  // Chuyển ID thành String

    private Role role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime lastLogin;

    private String email;
    private String familyName;
    private String givenName;

    // No-args constructor
    public User() {
        this.id = UUID.randomUUID().toString(); // Tạo ID duy nhất với UUID
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.lastLogin = LocalDateTime.now();
    }

    public User(Role role, String email, String familyName, String givenName) {
        this();
        this.role = role;
        this.email = email;
        this.familyName = familyName;
        this.givenName = givenName;
    }
}
