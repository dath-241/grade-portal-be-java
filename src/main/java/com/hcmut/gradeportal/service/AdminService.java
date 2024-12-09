package com.hcmut.gradeportal.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.hcmut.gradeportal.security.jwt.JwtService;
import com.hcmut.gradeportal.dtos.admin.request.CreateAdminRequest;
import com.hcmut.gradeportal.security.GoogleTokenVerifierService;
import com.hcmut.gradeportal.dtos.auth.response.AuthResponse;
import com.hcmut.gradeportal.dtos.halloffame.HallOfFameRequest;
import com.hcmut.gradeportal.entities.Admin;
import com.hcmut.gradeportal.entities.CourseClass;
import com.hcmut.gradeportal.entities.HallOfFame;
import com.hcmut.gradeportal.entities.SheetMark;
import com.hcmut.gradeportal.entities.Student;
import com.hcmut.gradeportal.entities.enums.ClassStatus;
import com.hcmut.gradeportal.entities.enums.Role;
import com.hcmut.gradeportal.repositories.AdminRepository;
import com.hcmut.gradeportal.repositories.CourseClassRepository;
import com.hcmut.gradeportal.repositories.SheetMarkRepository;
import com.hcmut.gradeportal.specification.CourseClassSpecification;

@Service
public class AdminService {
    private final AdminRepository adminRepository;
    private final CourseClassRepository courseClassRepository;
    private final SheetMarkRepository sheetMarkRepository;

    private final GoogleTokenVerifierService googleTokenVerifierService;
    private final JwtService jwtService;

    public AdminService(AdminRepository adminRepository,
            CourseClassRepository courseClassRepository,
            SheetMarkRepository sheetMarkRepository, GoogleTokenVerifierService googleTokenVerifierService,
            JwtService jwtService) {
        this.adminRepository = adminRepository;
        this.courseClassRepository = courseClassRepository;
        this.sheetMarkRepository = sheetMarkRepository;
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
    public List<HallOfFame> halloffame(HallOfFameRequest request) {
        List<HallOfFame> temp = new ArrayList<>();
        Specification<CourseClass> spec = Specification
                .where(CourseClassSpecification.hasCourseCode(request.getCourseCode()))
                .and(CourseClassSpecification.hasSemesterCode(request.getSemesterCode()))
                .and(CourseClassSpecification.hasClassStatus(ClassStatus.Completed));

        // Truy vấn dữ liệu từ database
        List<CourseClass> listClass = courseClassRepository.findAll(spec);
        for (CourseClass courseclass : listClass) {
            for (Student student : courseclass.getListOfStudents()) {
                Optional<SheetMark> sheetmark = sheetMarkRepository
                        .findByStudentIdAndCourseCodeAndSemesterCodeAndClassName(
                                student.getId(), courseclass.getCourseCode(), courseclass.getSemesterCode(),
                                courseclass.getClassName());

                HallOfFame hallOfFame = new HallOfFame(student, sheetmark.get().getBT(),
                        sheetmark.get().getTN(), sheetmark.get().getBTL(), sheetmark.get().getGK(),
                        sheetmark.get().getCK(), sheetmark.get().getFinalMark());
                int i = 0;
                for (; i < temp.size(); i++) {
                    if (hallOfFame.getFinalMark() > temp.get(i).getFinalMark())
                        break;
                }
                temp.add(i, hallOfFame);

            }
        }
        int a = request.getNoOfStudents();
        int i = a;
        double epsilon = 1e-9;

        if (i > temp.size()) {
            return temp;
        } else {
            List<HallOfFame> result;
            while (Math.abs(temp.get(a - 1).getFinalMark() - temp.get(i - 1).getFinalMark()) < epsilon
                    && i <= temp.size()) {
                i++;
                if (i > temp.size())
                    break;
            }

            result = temp.subList(0, i - 1);
            return result;
        }

    }

}
