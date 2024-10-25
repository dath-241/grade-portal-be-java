package com.hcmut.gradeportal.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import com.hcmut.gradeportal.dtos.teacher.request.CreateTeacherRequest;
import com.hcmut.gradeportal.dtos.teacher.request.GetTeacherRequest;
import com.hcmut.gradeportal.entities.Teacher;
import com.hcmut.gradeportal.entities.enums.Role;
import com.hcmut.gradeportal.repositories.TeacherRepository;

@Service
public class TeacherService {
    private final TeacherRepository teacherRepository;

    public TeacherService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    ////////////// Service for get method - read data //////////////
    // Lấy tất cả giáo viên trong hệ thống
    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

    // Lấy giáo viên theo ID của user
    public Teacher getTeacherById(String id) {
        return teacherRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Teacher not found"));
    }

    // Lấy giáo viên theo ID của giáo viên
    public Teacher getTeacherByTeacherId(String teacherId) {
        return teacherRepository.findByTeacherId(teacherId)
                .orElseThrow(() -> new IllegalArgumentException("Teacher not found"));
    }

    // Lấy danh sách giáo viên dựa trên các điều kiện tìm kiếm
    public List<Teacher> getTeachersBySpecification(GetTeacherRequest request) {
        // Kiểm tra và đặt các trường chuỗi rỗng thành null
        String email = request.getEmail() != null && request.getEmail().isEmpty() ? null : request.getEmail();
        String familyName = request.getFamilyName() != null && request.getFamilyName().isEmpty() ? null
                : request.getFamilyName();
        String givenName = request.getGivenName() != null && request.getGivenName().isEmpty() ? null
                : request.getGivenName();
        String phone = request.getPhone() != null && request.getPhone().isEmpty() ? null : request.getPhone();
        String faculty = request.getFaculty() != null && request.getFaculty().isEmpty() ? null : request.getFaculty();
        String teacherId = request.getTeacherId() != null && request.getTeacherId().isEmpty() ? null
                : request.getTeacherId();

        // Tạo một đối tượng Teacher với các trường được set từ request
        Teacher teacherProbe = new Teacher();
        teacherProbe.setEmail(email);
        teacherProbe.setFamilyName(familyName);
        teacherProbe.setGivenName(givenName);
        teacherProbe.setPhone(phone);
        teacherProbe.setFaculty(faculty);
        teacherProbe.setTeacherId(teacherId);

        // Tìm kiếm danh sách giáo viên với các điều kiện đã xác định
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreNullValues() // Bỏ qua các trường null
                .withStringMatcher(ExampleMatcher.StringMatcher.EXACT)
                .withIgnorePaths("createdAt", "updatedAt", "lastLogin", "role", "id"); // Bỏ qua các trường
                                                                                       // này

        // Tạo Example dựa trên probe và matcher
        Example<Teacher> example = Example.of(teacherProbe, matcher);

        // Tìm kiếm danh sách giáo viên dựa trên Example
        List<Teacher> teachers = teacherRepository.findAll(example);
        return teachers;
    }

    ////////////// Service for post method - create data //////////////
    // Tạo một giáo viên mới
    public Teacher createTeacher(CreateTeacherRequest request) {
        Optional<Teacher> optTeacher = teacherRepository.findByEmailAndRole(request.getEmail(), Role.TEACHER);
        if (optTeacher.isPresent()) {
            throw new IllegalArgumentException("Email is already in use.");
        } else {
            // Kiểm tra xem các trường hợp email và tên có hợp lệ không
            if (request.getEmail() == null || request.getEmail().isBlank()) {
                throw new IllegalArgumentException("Email is required.");
            }
            if (request.getFamilyName() == null || request.getFamilyName().isBlank()) {
                throw new IllegalArgumentException("Family name is required.");
            }
            if (request.getGivenName() == null || request.getGivenName().isBlank()) {
                throw new IllegalArgumentException("Given name is required.");
            }

            Teacher teacher = new Teacher();
            if (request.getTeacherId() != null) {
                teacher.setTeacherId(request.getTeacherId());
            }
            teacher.setEmail(request.getEmail());
            teacher.setFamilyName(request.getFamilyName());
            teacher.setGivenName(request.getGivenName());
            if (request.getPhone() != null) {
                teacher.setPhone(request.getPhone());
            }
            if (request.getFaculty() != null) {
                teacher.setFaculty(request.getFaculty());
            }
            teacher.setRole(Role.TEACHER);

            return teacherRepository.save(teacher);
        }
    }

    ////////////// Service for put method - update data //////////////

    ////////////// Service for delete method - delete data //////////////

}
