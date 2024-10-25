package com.hcmut.gradeportal.service;

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

    public Course createCourse(CreateCourseRequest createCourseRequest) {
        Course course = new Course();
        course.setCourseCode(createCourseRequest.getCourseCode());
        course.setCourseName(createCourseRequest.getCourseName());
        course.setCredit(createCourseRequest.getCredit());
        return courseRepository.save(course);
    }
}
