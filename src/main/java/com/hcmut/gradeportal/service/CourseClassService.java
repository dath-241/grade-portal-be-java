package com.hcmut.gradeportal.service;

import org.springframework.stereotype.Service;

import com.hcmut.gradeportal.dtos.courseClass.request.CreateCourseClassRequest;
import com.hcmut.gradeportal.entities.Course;
import com.hcmut.gradeportal.entities.CourseClass;
import com.hcmut.gradeportal.entities.Teacher;
import com.hcmut.gradeportal.repositories.CourseClassRepository;
import com.hcmut.gradeportal.repositories.CourseRepository;
import com.hcmut.gradeportal.repositories.TeacherRepository;

@Service
public class CourseClassService {
    private final CourseRepository courseRepository;
    private final CourseClassRepository courseClassRepository;

    private final TeacherRepository teacherRepository;

    public CourseClassService(CourseRepository courseRepository, CourseClassRepository courseClassRepository,
            TeacherRepository teacherRepository) {
        this.courseRepository = courseRepository;
        this.courseClassRepository = courseClassRepository;
        this.teacherRepository = teacherRepository;
    }

    // Hàm này dùng khi init dữ liệu, lúc này teacherId sẽ là teacherId của Teacher
    // chứ không phải là ID của Teacher-User
    public CourseClass createCourseClassWhenInit(CreateCourseClassRequest createCourseClassRequest) {
        CourseClass newClass = new CourseClass();

        // Tìm kiếm giáo viên bằng teacherId (giả sử teacherId là ID của giáo viên)
        Teacher teacher = teacherRepository.findByTeacherId(createCourseClassRequest.getTeacherId())
                .orElseThrow(() -> new IllegalArgumentException("Teacher not found"));

        Course course = courseRepository.findByCourseCode(createCourseClassRequest.getCourseCode())
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));

        newClass.setCourseCode(createCourseClassRequest.getCourseCode());
        newClass.setSemesterCode(createCourseClassRequest.getSemesterCode());
        newClass.setClassName(createCourseClassRequest.getClassName());
        newClass.setCourse(course);
        newClass.setTeacher(teacher);
        newClass.setClassStatus(createCourseClassRequest.getClassStatus());

        return courseClassRepository.save(newClass);
    }

    public CourseClass createCourseClass(CreateCourseClassRequest createCourseClassRequest) {
        CourseClass newClass = new CourseClass();

        // Tìm kiếm giáo viên bằng teacherId (giả sử teacherId là ID của giáo viên)
        Teacher teacher = teacherRepository.findById(createCourseClassRequest.getTeacherId())
                .orElseThrow(() -> new IllegalArgumentException("Teacher not found"));

        Course course = courseRepository.findByCourseCode(createCourseClassRequest.getCourseCode())
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));

        newClass.setCourseCode(createCourseClassRequest.getCourseCode());
        newClass.setSemesterCode(createCourseClassRequest.getSemesterCode());
        newClass.setClassName(createCourseClassRequest.getClassName());
        newClass.setCourse(course);
        newClass.setTeacher(teacher);
        newClass.setClassStatus(createCourseClassRequest.getClassStatus());

        return courseClassRepository.save(newClass);
    }
}
