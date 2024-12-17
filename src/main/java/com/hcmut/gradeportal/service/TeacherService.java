package com.hcmut.gradeportal.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import com.hcmut.gradeportal.dtos.auth.response.AuthResponse;
import com.hcmut.gradeportal.dtos.teacher.request.CreateTeacherRequest;
import com.hcmut.gradeportal.dtos.teacher.request.GetTeacherRequest;
import com.hcmut.gradeportal.dtos.teacher.request.UpdateTeacherRequest;
import com.hcmut.gradeportal.entities.CourseClass;
import com.hcmut.gradeportal.entities.SheetMark;
import com.hcmut.gradeportal.entities.Teacher;
import com.hcmut.gradeportal.entities.enums.Role;
import com.hcmut.gradeportal.repositories.CourseClassRepository;
import com.hcmut.gradeportal.repositories.SheetMarkRepository;
import com.hcmut.gradeportal.repositories.TeacherRepository;
import com.hcmut.gradeportal.security.GoogleTokenVerifierService;
import com.hcmut.gradeportal.security.jwt.JwtService;

import jakarta.transaction.Transactional;

@Service
public class TeacherService {
    private final TeacherRepository teacherRepository;
    private final SheetMarkRepository sheetMarkRepository;
    private final CourseClassRepository courseClassRepository;

    private final GoogleTokenVerifierService googleTokenVerifierService;
    private final JwtService jwtService;

    public TeacherService(TeacherRepository teacherRepository, GoogleTokenVerifierService googleTokenVerifierService,
            JwtService jwtService, SheetMarkRepository sheetMarkRepository,
            CourseClassRepository courseClassRepository) {
        this.teacherRepository = teacherRepository;
        this.googleTokenVerifierService = googleTokenVerifierService;
        this.jwtService = jwtService;
        this.sheetMarkRepository = sheetMarkRepository;
        this.courseClassRepository = courseClassRepository;
    }

    //////////// Service for login method ////////////
    // Login with google authentication
    public AuthResponse<Teacher> login(String idToken) {
        try {
            var payload = googleTokenVerifierService.verify(idToken);

            String email = payload.getEmail();

            Optional<Teacher> teacher = teacherRepository.findByEmailAndRole(email, Role.TEACHER);
            if (teacher.isPresent()) {
                String userId = teacher.get().getId();

                return new AuthResponse<>(jwtService.generateToken(userId, Role.TEACHER), teacher.get(), Role.TEACHER);
            } else {
                throw new IllegalArgumentException("Teacher not found");
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid ID token");
        }
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
    ///
    @Transactional
    public Teacher updateTeacher(UpdateTeacherRequest request) {
        if (request.getUserId() == null) {
            throw new IllegalArgumentException("Teacher User ID is required.");
        }

        Teacher teacher = teacherRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Teacher not found for ID: " + request.getUserId()));

        Optional.ofNullable(request.getNewEmail()).ifPresent(teacher::setEmail);
        Optional.ofNullable(request.getNewFamilyName()).ifPresent(teacher::setFamilyName);
        Optional.ofNullable(request.getNewGivenName()).ifPresent(teacher::setGivenName);
        Optional.ofNullable(request.getNewPhone()).ifPresent(teacher::setPhone);
        Optional.ofNullable(request.getNewFaculty()).ifPresent(teacher::setFaculty);
        Optional.ofNullable(request.getNewTeacherId()).ifPresent(teacher::setTeacherId);

        return teacherRepository.save(teacher);
    }

    ////////////// Service for delete method - delete data //////////////
    // Xóa giáo viên dựa trên ID của user
    @Transactional
    public void deleteTeacher(String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Teacher ID is required.");
        }

        // Kiểm tra xem Teacher có tồn tại không
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Teacher not found for ID: " + id));

        // Xóa hoặc cập nhật các mối quan hệ liên quan

        // Xóa bảng điểm (SheetMark)
        List<SheetMark> sheetMarks = sheetMarkRepository.findByTeacherId(id);
        sheetMarkRepository.deleteAll(sheetMarks);

        // Xóa hoặc cập nhật CourseClass
        List<CourseClass> courseClasses = courseClassRepository.findByTeacher_Id(id);
        for (CourseClass courseClass : courseClasses) {
            courseClass.setTeacher(null); // Có thể đặt Teacher của lớp học là null
            courseClassRepository.save(courseClass); // Lưu lại lớp học
        }

        // Xóa Teacher
        teacherRepository.delete(teacher);
    }
}
