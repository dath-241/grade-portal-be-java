package com.hcmut.gradeportal.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.Data;
import jakarta.persistence.MappedSuperclass;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.hcmut.gradeportal.entities.enums.Role;

@Data
@MappedSuperclass
public abstract class User {

    @Id
    private String id; // Chuyển ID thành String

    @NotNull
    @NotBlank
    private Role role;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime lastLogin;

    @NotBlank
    @Column(unique = true)
    private String email;
    private String familyName;
    private String givenName;
    private String phone;

    private String Faculty;

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
