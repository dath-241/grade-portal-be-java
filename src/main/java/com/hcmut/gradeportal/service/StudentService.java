package com.hcmut.gradeportal.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hcmut.gradeportal.dtos.student.request.CreateStudentRequest;
import com.hcmut.gradeportal.entities.Student;
import com.hcmut.gradeportal.entities.User;
import com.hcmut.gradeportal.entities.enums.Role;
import com.hcmut.gradeportal.repositories.StudentRepository;
import com.hcmut.gradeportal.repositories.UserRepository;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final UserRepository userRepository;

    public StudentService (StudentRepository studentRepository, UserRepository userRepository) {
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
    }

// Service for get method - read data


// Service for post method - create data
    // Tạo một sinh viên mới
    public Student createStudent(CreateStudentRequest request) {
        Optional<User> user= userRepository.findByEmailAndRole(request.getEmail(), Role.STUDENT);
        if(user.isPresent()) {
            return studentRepository.findById(user.get().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Failed to create student"));
        } else {
            Student student;
            if( request.getStudentId() != null ) {
                student = new Student(request.getStudentId());
            } else {
                student = new Student();
            }
            student.setEmail(request.getEmail());
            student.setFamilyName(request.getFamilyName());
            student.setGivenName(request.getGivenName());
            student.setRole(Role.STUDENT);

            return studentRepository.save(student);
        }
    }

// Service for put method - update data
    


// Service for delete method - delete data

}
