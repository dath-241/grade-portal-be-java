package com.hcmut.gradeportal.controller.student;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hcmut.gradeportal.dtos.courseClass.CourseClassDto;
import com.hcmut.gradeportal.dtos.courseClass.CourseClassDtoConverter;
import com.hcmut.gradeportal.dtos.courseClass.request.GetCourseClassRequest;
import com.hcmut.gradeportal.dtos.teacher.TeacherDto;
import com.hcmut.gradeportal.dtos.teacher.TeacherDtoConverter;
import com.hcmut.gradeportal.entities.CourseClass;
import com.hcmut.gradeportal.entities.Teacher;
import com.hcmut.gradeportal.response.ApiResponse;
import com.hcmut.gradeportal.service.CourseClassService;

@RestController
@RequestMapping("/student/class-info")
public class StudentClassInfoController {

    private final CourseClassService courseClassService;
    private final CourseClassDtoConverter courseClassDtoConverter;
    private final TeacherDtoConverter teacherDtoConverter;

    public StudentClassInfoController(CourseClassService courseClassService,
            CourseClassDtoConverter courseClassDtoConverter, TeacherDtoConverter teacherDtoConverter) {
        this.courseClassService = courseClassService;
        this.courseClassDtoConverter = courseClassDtoConverter;
        this.teacherDtoConverter = teacherDtoConverter;
    }

    @GetMapping("/get-class-by-student-id/{studentId}")
    public ResponseEntity<ApiResponse<List<CourseClassDto>>> getClassByStudentId(@PathVariable String studentId) {
        try {
            List<CourseClassDto> courseClassDtos = courseClassDtoConverter
                    .convert(courseClassService.getClassByStudentId(studentId));
            ApiResponse<List<CourseClassDto>> response = new ApiResponse<>(HttpStatus.OK.value(), "Get classes",
                    courseClassDtos);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalStateException | IllegalArgumentException e) {

            ApiResponse<List<CourseClassDto>> response = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(),
                    e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            // Phản hồi lỗi
            ApiResponse<List<CourseClassDto>> response = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Failed to fetch classes", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get-class-by-student-user-id/{userId}")
    public ResponseEntity<ApiResponse<List<CourseClassDto>>> getClassByStudentUserId(@PathVariable String userId) {
        try {
            List<CourseClassDto> courseClassDtos = courseClassDtoConverter
                    .convert(courseClassService.getClassByStudentUserId(userId));
            ApiResponse<List<CourseClassDto>> response = new ApiResponse<>(HttpStatus.OK.value(), "Get classes",
                    courseClassDtos);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalStateException | IllegalArgumentException e) {

            ApiResponse<List<CourseClassDto>> response = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(),
                    e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            // Phản hồi lỗi
            ApiResponse<List<CourseClassDto>> response = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Failed to fetch classes", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // get class by specification
    @GetMapping("/student-get-class-by-specification/{studentId}")
    public ResponseEntity<ApiResponse<List<CourseClassDto>>> getClassBySpecification(
            @PathVariable String studentId,
            @RequestBody GetCourseClassRequest request) {
        try {
            List<CourseClass> classes = courseClassService.studentGetClassBySpecification(studentId, request);
            List<CourseClassDto> courseClassDtos = courseClassDtoConverter.convert(classes);
            ApiResponse<List<CourseClassDto>> response = new ApiResponse<>(HttpStatus.OK.value(),
                    "Get class by specification",
                    courseClassDtos);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            ApiResponse<List<CourseClassDto>> response = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(),
                    e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            ApiResponse<List<CourseClassDto>> response = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // get teacher info by class
    @GetMapping("/get-teacher-info-by-class/{courseCode}/{semesterCode}/{className}")
    public ResponseEntity<ApiResponse<TeacherDto>> getTeacherInfoByClass(
            @PathVariable String courseCode,
            @PathVariable String semesterCode,
            @PathVariable String className) {
        try {
            Teacher teacher = courseClassService.getTeacherByClass(courseCode, semesterCode, className);
            TeacherDto teacherDto = teacherDtoConverter.convert(teacher);

            ApiResponse<TeacherDto> response = new ApiResponse<>(
                    HttpStatus.OK.value(),
                    "Teacher information retrieved successfully",
                    teacherDto);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            ApiResponse<TeacherDto> response = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(),
                    e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            ApiResponse<TeacherDto> response = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    ///////////////// All Get request for student class info /////////////////

}
