package com.hcmut.gradeportal.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hcmut.gradeportal.dtos.teacher.request.CreateTeacherRequest;
import com.hcmut.gradeportal.entities.Teacher;
import com.hcmut.gradeportal.entities.User;
import com.hcmut.gradeportal.entities.enums.Role;
import com.hcmut.gradeportal.repositories.TeacherRepository;
import com.hcmut.gradeportal.repositories.UserRepository;

@Service
public class TeacherService {
    private final UserRepository userRepository;
    private final TeacherRepository teacherRepository;

    public TeacherService (TeacherRepository teacherRepository, UserRepository userRepository) {
        this.userRepository = userRepository;
        this.teacherRepository = teacherRepository;
    }

// Service for get method - read data


// Service for post method - create data
    // Tạo một giáo viên mới
    public Teacher createTeacher(CreateTeacherRequest request) {
        Optional<User> user= userRepository.findByEmailAndRole(request.getEmail(), Role.TEACHER);
        if(user.isPresent()) {
            return teacherRepository.findById(user.get().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Failed to create teacher"));
        } else {
            Teacher teacher;
            if( request.getTeacherId() != null ) {
                teacher = new Teacher(request.getTeacherId());
            } else {
                teacher = new Teacher();
            }
            teacher.setEmail(request.getEmail());
            teacher.setFamilyName(request.getFamilyName());
            teacher.setGivenName(request.getGivenName());
            teacher.setRole(Role.TEACHER);

            return teacherRepository.save(teacher);
        }
    }

// Service for put method - update data
    


// Service for delete method - delete data

}
