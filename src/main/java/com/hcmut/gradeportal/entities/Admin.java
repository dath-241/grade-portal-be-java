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

    // Getters and Setters

    public String getId() {
        return this.id;
    }

    public Role getRole() {
        return this.role;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return this.updatedAt;
    }

    public void setUpdatedAt() {
        this.updatedAt = LocalDateTime.now();
    }

    public LocalDateTime getLastLogin() {
        return this.lastLogin;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
        this.updatedAt = LocalDateTime.now();
    }

    public String getFamilyName() {
        return this.familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
        this.updatedAt = LocalDateTime.now();
    }

    public String getGivenName() {
        return this.givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
        this.updatedAt = LocalDateTime.now();
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
        this.updatedAt = LocalDateTime.now();
    }

    public String getFaculty() {
        return this.Faculty;
    }

    public void setFaculty(String Faculty) {
        this.Faculty = Faculty;
        this.updatedAt = LocalDateTime.now();
    }

}
