package com.hcmut.gradeportal.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hcmut.gradeportal.dtos.course.request.CreateCourseRequest;
import com.hcmut.gradeportal.entities.Course;
import com.hcmut.gradeportal.repositories.CourseRepository;

@Service
public class CourseService {
    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    ////////////// Service for get method - read data //////////////
    // Lấy ra tất cả các Course trong hệ thống
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    // Lấy ra Course theo courseCode
    public Course getCourseByCourseCode(String courseCode) {
        return courseRepository.findByCourseCode(courseCode)
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));
    }

    // Lấy ra Course theo số tín chỉ
    public List<Course> getCourseByCredit(int credit) {
        return courseRepository.findByCredit(credit);
    }

    ////////////// Service for post method - create data //////////////
    // Tạo một Course mới
    public Course createCourse(CreateCourseRequest createCourseRequest) {
        Optional<Course> courseOptional = courseRepository.findByCourseCode(createCourseRequest.getCourseCode());
        if (courseOptional.isPresent()) {
            throw new IllegalArgumentException("Course code already exists");
        }
        Course course = new Course();
        course.setCourseCode(createCourseRequest.getCourseCode());
        course.setCourseName(createCourseRequest.getCourseName());
        course.setCredit(createCourseRequest.getCredit());

        Integer totalCoefficient = 0;

        if (createCourseRequest.getCoefficient_of_BT() == null) {
            throw new IllegalArgumentException("Coefficient of BT is required");
        } else {
            totalCoefficient += createCourseRequest.getCoefficient_of_BT();
            course.setCoefficient_of_BT(createCourseRequest.getCoefficient_of_BT());
        }

        if (createCourseRequest.getCoefficient_of_BTL() == null) {
            throw new IllegalArgumentException("Coefficient of TH is required");
        } else {
            totalCoefficient += createCourseRequest.getCoefficient_of_BTL();
            course.setCoefficient_of_BTL(createCourseRequest.getCoefficient_of_BTL());
        }

        if (createCourseRequest.getCoefficient_of_TN() == null) {
            throw new IllegalArgumentException("Coefficient of TH is required");
        } else {
            totalCoefficient += createCourseRequest.getCoefficient_of_TN();
            course.setCoefficient_of_TN(createCourseRequest.getCoefficient_of_TN());
        }

        if (createCourseRequest.getCoefficient_of_GK() == null) {
            throw new IllegalArgumentException("Coefficient of GK is required");
        } else {
            totalCoefficient += createCourseRequest.getCoefficient_of_GK();
            course.setCoefficient_of_GK(createCourseRequest.getCoefficient_of_GK());
        }

        if (createCourseRequest.getCoefficient_of_CK() == null) {
            throw new IllegalArgumentException("Coefficient of CK is required");
        } else {
            totalCoefficient += createCourseRequest.getCoefficient_of_CK();
            course.setCoefficient_of_CK(createCourseRequest.getCoefficient_of_CK());
        }

        if (totalCoefficient != 100) {
            throw new IllegalArgumentException("Total coefficient must be 100");
        }

        return courseRepository.save(course);
    }
}
