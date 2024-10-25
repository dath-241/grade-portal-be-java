package com.hcmut.gradeportal.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.validation.constraints.NotBlank;

import com.hcmut.gradeportal.entities.enums.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "admin")
public class Admin {

    @Id
    private String id; // Chuyển ID thành String

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
    public Admin() {
        this.id = UUID.randomUUID().toString(); // Tạo ID duy nhất với UUID
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.lastLogin = LocalDateTime.now();
        this.role = Role.ADMIN;
    }

    public Admin(String email, String familyName, String givenName, String phone, String Faculty) {
        this();
        this.email = email;
        this.familyName = familyName;
        this.givenName = givenName;
        this.phone = phone;
        this.Faculty = Faculty;
    }

}
