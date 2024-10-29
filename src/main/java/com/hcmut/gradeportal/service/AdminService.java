package com.hcmut.gradeportal.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hcmut.gradeportal.dtos.admin.request.CreateAdminRequest;
import com.hcmut.gradeportal.entities.Admin;
import com.hcmut.gradeportal.entities.enums.Role;
import com.hcmut.gradeportal.repositories.AdminRepository;

@Service
public class AdminService {
    private final AdminRepository adminRepository;

    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
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
