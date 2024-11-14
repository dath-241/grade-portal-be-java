package com.hcmut.gradeportal.controller.teacher;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hcmut.gradeportal.dtos.courseClass.CourseClassDto;
import com.hcmut.gradeportal.dtos.courseClass.request.AddStudentRequest;
import com.hcmut.gradeportal.dtos.courseClass.request.RemoveStudentRequest;
import com.hcmut.gradeportal.response.ApiResponse;
import com.hcmut.gradeportal.service.CourseClassService;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/teacher/manage-class")
public class TeacherManageClassController {
    private final CourseClassService courseClassService;

    public TeacherManageClassController(CourseClassService courseClassService) {
        this.courseClassService = courseClassService; 
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
