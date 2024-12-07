package com.hcmut.gradeportal.controller.teacher;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hcmut.gradeportal.dtos.courseClass.CourseClassDto;
import com.hcmut.gradeportal.dtos.courseClass.CourseClassDtoConverter;
import com.hcmut.gradeportal.dtos.courseClass.request.AddStudentRequest;
import com.hcmut.gradeportal.dtos.courseClass.request.GetCourseClassRequest;
import com.hcmut.gradeportal.dtos.courseClass.request.RemoveStudentRequest;
import com.hcmut.gradeportal.dtos.student.StudentDto;
import com.hcmut.gradeportal.dtos.student.StudentDtoConverter;
import com.hcmut.gradeportal.entities.CourseClass;
import com.hcmut.gradeportal.entities.Student;
import com.hcmut.gradeportal.response.ApiResponse;
import com.hcmut.gradeportal.service.CourseClassService;

@RestController
@RequestMapping("/teacher/manage-class")
public class TeacherManageClassController {
    private final CourseClassService courseClassService;
    private final CourseClassDtoConverter courseClassDtoConverter;
    private final StudentDtoConverter studentDtoConverter;

    public TeacherManageClassController(CourseClassService courseClassService, CourseClassDtoConverter courseClassDtoConverter, StudentDtoConverter StudentDtoConverter) {
        this.courseClassService = courseClassService; 
        this.courseClassDtoConverter = courseClassDtoConverter;
        this.studentDtoConverter = StudentDtoConverter;
    }


    //Xem các lớp đã và đang giảng dạy
    //bằng teacher ID
    @GetMapping("/get-class-by-teacher-id/{teacherId}")
    public ResponseEntity<ApiResponse<List<CourseClassDto>>> getClassByTeacherId(@PathVariable String teacherId) {
        try {
            List<CourseClassDto> courseClassDtos = courseClassDtoConverter
                    .convert(courseClassService.getClassByTeacherId(teacherId));
            ApiResponse<List<CourseClassDto>> response = new ApiResponse<>(HttpStatus.OK.value(), "Get classes",
                    courseClassDtos);
            
            return new ResponseEntity<>(response, HttpStatus.OK);  
        } catch (IllegalStateException | IllegalArgumentException e) {
    
            ApiResponse<List<CourseClassDto>> response = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            // Phản hồi lỗi
            ApiResponse<List<CourseClassDto>> response = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Failed to fetch classes", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    //Bằng user ID
    @GetMapping("/get-class-by-teacher-user-id/{UserId}")
    public ResponseEntity<ApiResponse<List<CourseClassDto>>> getClassByTeacherUserId(@PathVariable String UserId) {
        try {
            List<CourseClassDto> courseClassDtos = courseClassDtoConverter
                    .convert(courseClassService.getClassByTeacherUserId(UserId));
            ApiResponse<List<CourseClassDto>> response = new ApiResponse<>(HttpStatus.OK.value(), "Get classes",
                    courseClassDtos);
            
            return new ResponseEntity<>(response, HttpStatus.OK);  
        } catch (IllegalStateException | IllegalArgumentException e) {
    
            ApiResponse<List<CourseClassDto>> response = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            // Phản hồi lỗi
            ApiResponse<List<CourseClassDto>> response = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Failed to fetch classes", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    // get class by specification
    @GetMapping("/teacher-get-class-by-specification/{teacherId}")
    public ResponseEntity<ApiResponse<List<CourseClassDto>>> getClassBySpecification(
            @PathVariable String teacherId,
            @RequestBody GetCourseClassRequest request) {
        try {
            List<CourseClass> classes = courseClassService.teacherGetClassBySpecification(teacherId, request);
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
    // get student info by class
    @GetMapping("/get-student-info-by-class/{teacherId}/{courseCode}/{semesterCode}/{className}")
    public ResponseEntity<ApiResponse<List<StudentDto>>> getStudentInfoByClass(
            @PathVariable String teacherId,
            @PathVariable String courseCode,
            @PathVariable String semesterCode,
            @PathVariable String className) {
        try {
            List<Student> students = courseClassService.getStudentsByTeacherClass(teacherId, courseCode, semesterCode, className);

            List<StudentDto> studentDtos = studentDtoConverter.convert(students);

            ApiResponse<List<StudentDto>> response = new ApiResponse<>(
                    HttpStatus.OK.value(),
                    "Student information retrieved successfully",
                    studentDtos
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            ApiResponse<List<StudentDto>> response = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(),
                    e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            ApiResponse<List<StudentDto>> response = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Thêm sinh viên vào lớp học
    @PutMapping("/add-student")
    public ResponseEntity<ApiResponse<CourseClassDto>> addStudentToClass(@RequestBody AddStudentRequest request) {
        try {    
            // Thêm sinh viên vào lớp học
            courseClassService.addStudentToClass(request);
    
            // Phản hồi thành công
            ApiResponse<CourseClassDto> response = new ApiResponse<>(HttpStatus.OK.value(), "Student added successfully", null);
            return new ResponseEntity<>(response, HttpStatus.OK);
            
        } catch (IllegalStateException | IllegalArgumentException e) {
    
            ApiResponse<CourseClassDto> response = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            
        } catch (Exception e) {
    
            ApiResponse<CourseClassDto> response = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Failed to add student", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    


    // Xóa sinh viên khỏi lớp học
    @DeleteMapping("/remove-student")
    public ResponseEntity<ApiResponse<CourseClassDto>> removeStudentFromClass(@RequestBody RemoveStudentRequest request) {
        try {
            // Xóa sinh viên khỏi lớp học
            courseClassService.removeStudentFromClass(request);

            // Phản hồi thành công
            ApiResponse<CourseClassDto> response = new ApiResponse<>(HttpStatus.OK.value(), "Student removed successfully", null);
            return new ResponseEntity<>(response, HttpStatus.OK);
            
        } catch (IllegalStateException | IllegalArgumentException e) {

            ApiResponse<CourseClassDto> response = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            
        } catch (Exception e) {

            ApiResponse<CourseClassDto> response = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Failed to remove student", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
