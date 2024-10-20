package com.hcmut.gradeportal.service;

import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.hcmut.gradeportal.dtos.user.request.CreateUserRequest;
import com.hcmut.gradeportal.entities.Student;
import com.hcmut.gradeportal.entities.Teacher;
import com.hcmut.gradeportal.entities.User;
import com.hcmut.gradeportal.entities.enums.Role;
import com.hcmut.gradeportal.repositories.StudentRepository;
import com.hcmut.gradeportal.repositories.TeacherRepository;
import com.hcmut.gradeportal.repositories.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;

    public UserService (UserRepository userRepository, TeacherRepository teacherRepository, StudentRepository studentRepository) {
        this.userRepository = userRepository;
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
    }

// Service for get method - read data
    // Tìm tất cả người dùng
    public Set<User> getAllUsers() {
        return userRepository.findAlls();
    }



// Service for post method - create data
    // Tạo một người dùng mới
    public User createUser(CreateUserRequest request) {
        if( request.getRole().equals(Role.TEACHER) ) {
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
        } else if ( request.getRole().equals(Role.STUDENT) ) {
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
        } else {
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
    }



// Service for put method - update data
    // Lưu một user mới hoặc cập nhật user đã có
    public User saveUser(User user) {
        return userRepository.save(user);
    }


// Service for delete method - delete data
    // Xóa người dùng theo ID
    public void deleteUserById(String id) {
        userRepository.deleteById(id);
    }
}
