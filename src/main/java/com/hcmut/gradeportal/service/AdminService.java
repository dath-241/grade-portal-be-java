package com.hcmut.gradeportal.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hcmut.gradeportal.dtos.admin.request.CreateAdminRequest;
import com.hcmut.gradeportal.entities.User;
import com.hcmut.gradeportal.entities.enums.Role;
import com.hcmut.gradeportal.repositories.UserRepository;

@Service
public class AdminService {
        private final UserRepository userRepository;

    public AdminService (UserRepository userRepository) {
        this.userRepository = userRepository;
    }

// Service for get method - read data


// Service for post method - create data
   // Tạo một admin mới
    public User createAdmin(CreateAdminRequest request) {
        Optional<User> user= userRepository.findByEmailAndRole(request.getEmail(), Role.ADMIN);
        if(user.isPresent()) {
            return userRepository.findById(user.get().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Failed to create admin"));
        } else {
            User user1 = new User();
            user1.setEmail(request.getEmail());
            user1.setFamilyName(request.getFamilyName());
            user1.setGivenName(request.getGivenName());
            user1.setRole(Role.ADMIN);

            return userRepository.save(user1);
        }
    }

// Service for put method - update data
    


// Service for delete method - delete data

}
