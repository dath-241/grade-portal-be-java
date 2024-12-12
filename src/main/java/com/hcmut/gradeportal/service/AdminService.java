package com.hcmut.gradeportal.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import com.hcmut.gradeportal.security.jwt.JwtService;
import com.hcmut.gradeportal.dtos.admin.request.CreateAdminRequest;
import com.hcmut.gradeportal.dtos.admin.request.GetAdminRequest;
import com.hcmut.gradeportal.dtos.admin.request.UpdateAdminRequest;
import com.hcmut.gradeportal.security.GoogleTokenVerifierService;
import com.hcmut.gradeportal.dtos.auth.response.AuthResponse;
import com.hcmut.gradeportal.entities.Admin;
import com.hcmut.gradeportal.entities.enums.Role;
import com.hcmut.gradeportal.repositories.AdminRepository;

import jakarta.transaction.Transactional;

@Service
public class AdminService {
    private final AdminRepository adminRepository;

    private final GoogleTokenVerifierService googleTokenVerifierService;
    private final JwtService jwtService;

    public AdminService(AdminRepository adminRepository,
            GoogleTokenVerifierService googleTokenVerifierService,
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

    // Lấy tât cả admin trong hệ thống
    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    // Lấy danh sách admin dựa trên các điều kiện tìm kiếm
    public List<Admin> getAdminsBySpecification(GetAdminRequest request) {
        // Kiểm tra và đặt các trường chuỗi rỗng thành null
        String email = request.getEmail() != null && request.getEmail().isEmpty() ? null : request.getEmail();
        String familyName = request.getFamilyName() != null && request.getFamilyName().isEmpty() ? null
                : request.getFamilyName();
        String givenName = request.getGivenName() != null && request.getGivenName().isEmpty() ? null
                : request.getGivenName();
        String phone = request.getPhone() != null && request.getPhone().isEmpty() ? null : request.getPhone();
        String faculty = request.getFaculty() != null && request.getFaculty().isEmpty() ? null : request.getFaculty();

        // Tạo một đối tượng Admin với các trường được set từ request
        Admin adminProbe = new Admin();
        adminProbe.setEmail(email);
        adminProbe.setFamilyName(familyName);
        adminProbe.setGivenName(givenName);
        adminProbe.setPhone(phone);
        adminProbe.setFaculty(faculty);

        // Tạo một Example từ adminProbe
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreNullValues() // Bỏ qua các trường null
                .withStringMatcher(ExampleMatcher.StringMatcher.EXACT)
                .withIgnorePaths("createdAt", "updatedAt", "lastLogin", "role", "id"); // Bỏ qua các trường này

        Example<Admin> example = Example.of(adminProbe, matcher);
        // Tìm kiếm admin dựa trên example
        return adminRepository.findAll(example);
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
    @Transactional
    public Admin updateAdmin(UpdateAdminRequest request) {
        if (request.getAdminId() == null) {
            throw new IllegalArgumentException("Admin id is required.");
        }

        Admin admin = adminRepository.findById(request.getAdminId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Admin not found for Admin id: " + request.getAdminId()));

        Optional.ofNullable(request.getNewEmail()).ifPresent(admin::setEmail);
        Optional.ofNullable(request.getNewFamilyName()).ifPresent(admin::setFamilyName);
        Optional.ofNullable(request.getNewGivenName()).ifPresent(admin::setGivenName);
        Optional.ofNullable(request.getNewPhone()).ifPresent(admin::setPhone);
        Optional.ofNullable(request.getNewFaculty()).ifPresent(admin::setFaculty);

        return adminRepository.save(admin);
    }

    ////////////// Service for delete method - delete data //////////////
    // Xóa một admin
    public Admin deleteAdmin(String id) {
        Optional<Admin> admin = adminRepository.findById(id);
        if (admin.isEmpty()) {
            throw new IllegalArgumentException("Admin not found");
        }

        adminRepository.deleteById(id);
        return admin.get();
    }
}
