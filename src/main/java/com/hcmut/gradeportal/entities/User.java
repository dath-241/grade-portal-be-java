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

    private String faculty;

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

    public String getId() {
        return this.id;
    }

    public Role getRole() {
        return this.role;
    }

    public void setRole(Role role) {
        this.role = role;
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
        return this.faculty;
    }

    public void setFaculty(String Faculty) {
        this.faculty = Faculty;
        this.updatedAt = LocalDateTime.now();
    }

}
