package com.hcmut.gradeportal.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import com.hcmut.gradeportal.dtos.student.request.CreateStudentRequest;
import com.hcmut.gradeportal.dtos.student.request.GetStudentRequest;
import com.hcmut.gradeportal.entities.Student;
import com.hcmut.gradeportal.entities.enums.Role;
import com.hcmut.gradeportal.repositories.StudentRepository;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    ////////////// Service for get method - read data //////////////
    // Lấy tất cả sinh viên trong hệ thống
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    // Lấy sinh viên theo id của user
    public Student getStudentById(String id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));
    }

    // Lấy sinh viên theo id của sinh viên
    public Student getStudentByStudentId(String studentId) {
        return studentRepository.findByStudentId(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));
    }

    // Lấy danh sách sinh viên dựa trên các điều kiện tìm kiếm
    public List<Student> getStudentsBySpecification(GetStudentRequest request) {
        // Kiểm tra và đặt các trường chuỗi rỗng thành null
        String email = request.getEmail() != null && request.getEmail().isEmpty() ? null : request.getEmail();
        String familyName = request.getFamilyName() != null && request.getFamilyName().isEmpty() ? null
                : request.getFamilyName();
        String givenName = request.getGivenName() != null && request.getGivenName().isEmpty() ? null
                : request.getGivenName();
        String phone = request.getPhone() != null && request.getPhone().isEmpty() ? null : request.getPhone();
        String faculty = request.getFaculty() != null && request.getFaculty().isEmpty() ? null : request.getFaculty();
        String studentId = request.getStudentId() != null && request.getStudentId().isEmpty() ? null
                : request.getStudentId();

        // Tạo một đối tượng Student với các trường được set từ request
        Student studentProbe = new Student();
        studentProbe.setEmail(email);
        studentProbe.setFamilyName(familyName);
        studentProbe.setGivenName(givenName);
        studentProbe.setPhone(phone);
        studentProbe.setFaculty(faculty);
        studentProbe.setStudentId(studentId);

        // Tạo một Example từ studentProbe
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreNullValues() // Bỏ qua các trường null
                .withStringMatcher(ExampleMatcher.StringMatcher.EXACT)
                .withIgnorePaths("createdAt", "updatedAt", "lastLogin", "role", "id"); // Bỏ qua các trường
                                                                                       // này

        Example<Student> example = Example.of(studentProbe, matcher);
        // Tìm kiếm sinh viên dựa trên example
        return studentRepository.findAll(example);
    }

    ////////////// Service for post method - create data //////////////
    // Tạo một sinh viên mới
    public Student createStudent(CreateStudentRequest request) {
        Optional<Student> student = studentRepository.findByEmailAndRole(request.getEmail(), Role.STUDENT);
        if (student.isPresent()) {
            throw new IllegalArgumentException("Email is already in use.");
        } else {
            if (request.getEmail() == null || request.getEmail().isBlank()) {
                throw new IllegalArgumentException("Email is required.");
            }

            if (request.getFamilyName() == null || request.getFamilyName().isBlank()) {
                throw new IllegalArgumentException("Family name is required.");
            }

            if (request.getGivenName() == null || request.getGivenName().isBlank()) {
                throw new IllegalArgumentException("Given name is required.");
            }

            Student curStudent = new Student();
            if (request.getStudentId() != null) {
                curStudent.setStudentId(request.getStudentId());
            }
            curStudent.setEmail(request.getEmail());
            curStudent.setFamilyName(request.getFamilyName());
            curStudent.setGivenName(request.getGivenName());
            if (request.getPhone() != null) {
                curStudent.setPhone(request.getPhone());
            }
            if (request.getFaculty() != null) {
                curStudent.setFaculty(request.getFaculty());
            }
            curStudent.setRole(Role.STUDENT);

            return studentRepository.save(curStudent);
        }
    }

    ////////////// Service for put method - update data //////////////

    ////////////// Service for delete method - delete data //////////////

}