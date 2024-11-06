package com.hcmut.gradeportal.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hcmut.gradeportal.dtos.admin.request.CreateAdminRequest;
import com.hcmut.gradeportal.dtos.auth.response.AuthResponse;
import com.hcmut.gradeportal.entities.Admin;
import com.hcmut.gradeportal.entities.enums.Role;
import com.hcmut.gradeportal.repositories.AdminRepository;
import com.hcmut.gradeportal.security.GoogleTokenVerifierService;
import com.hcmut.gradeportal.security.jwt.JwtService;

@Service
public class AdminService {
    private final AdminRepository adminRepository;
    private final GoogleTokenVerifierService googleTokenVerifierService;
    private final JwtService jwtService;

    public AdminService(AdminRepository adminRepository, GoogleTokenVerifierService googleTokenVerifierService,
            JwtService jwtService) {
        this.adminRepository = adminRepository;
        this.googleTokenVerifierService = googleTokenVerifierService;
        this.jwtService = jwtService;
    }

    //////////// Service for login method ////////////
    // Login with google authentication
    public AuthResponse<Admin> login(String idToken) {
        try {
            var payload = googleTokenVerifierService.verify(idToken);

            String email = payload.getEmail();
            String userId = payload.getSubject();

            Optional<Admin> admin = adminRepository.findByEmailAndRole(email, Role.ADMIN);
            if (admin.isPresent()) {
                return new AuthResponse<>(jwtService.generateToken(userId, Role.ADMIN), admin.get(), Role.ADMIN);
            } else {
                throw new IllegalArgumentException("Admin not found");
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid ID token");
        }
    }

    ////////////// Service for get method - read data //////////////
    // Lấy thông tin cá nhân
    public Admin getAdminById(String id) {
        Optional<Admin> admin = adminRepository.findById(id);
        if (admin.isPresent()) {
            return admin.get();
        } else {
            throw new IllegalArgumentException("Admin not found");
        }
    }

    ////////////// Service for post method - create data //////////////
    // Tạo một admin mới
    public Admin createAdmin(CreateAdminRequest request) {
        Optional<Admin> admin = adminRepository.findByEmailAndRole(request.getEmail(), Role.ADMIN);
        if (admin.isPresent()) {
            throw new IllegalArgumentException("Admin already exists");
        } else {
            Admin admin1 = new Admin();
            admin1.setEmail(request.getEmail());
            admin1.setFamilyName(request.getFamilyName());
            admin1.setGivenName(request.getGivenName());
            admin1.setPhone(request.getPhone());
            admin1.setFaculty(request.getFaculty());
            admin1.setRole(Role.ADMIN);

            return adminRepository.save(admin1);
        }
    }

    ////////////// Service for put method - update data //////////////

    ////////////// Service for delete method - delete data //////////////

}
