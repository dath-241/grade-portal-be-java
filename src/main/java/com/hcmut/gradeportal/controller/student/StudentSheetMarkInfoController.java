package com.hcmut.gradeportal.controller.student;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hcmut.gradeportal.dtos.sheetMark.SheetMarkDtoForStudent;
import com.hcmut.gradeportal.dtos.sheetMark.SheetMarkDtoForStudentConverter;
import com.hcmut.gradeportal.dtos.sheetMark.request.GetSheetMarkRequest;
import com.hcmut.gradeportal.dtos.student.response.StudentStatisticsResponse;
import com.hcmut.gradeportal.response.ApiResponse;
import com.hcmut.gradeportal.security.utils.CurrentUser;
import com.hcmut.gradeportal.service.SheetMarkService;

@RestController
@RequestMapping("/student/sheetmark-info")
public class StudentSheetMarkInfoController {
    private final SheetMarkDtoForStudentConverter sheetMarkDtoForStudentConverter;

    private final SheetMarkService sheetMarkService;

    public StudentSheetMarkInfoController(SheetMarkService sheetMarkService,
            SheetMarkDtoForStudentConverter sheetMarkDtoForStudentConverter) {
        this.sheetMarkService = sheetMarkService;
        this.sheetMarkDtoForStudentConverter = sheetMarkDtoForStudentConverter;
    }

    ///////////////// All Get request for student sheet mark info /////////////////
    // Get Sheet Mark By Student Id
    @GetMapping("/get-all")
    public ResponseEntity<ApiResponse<List<SheetMarkDtoForStudent>>> getSheetMarkByStudentId() {
        try {
            String id = CurrentUser.getUserId();

            List<SheetMarkDtoForStudent> sheetMarkDtos = sheetMarkDtoForStudentConverter
                    .convert(sheetMarkService.getSheetMarkByStudentId(id));
            ApiResponse<List<SheetMarkDtoForStudent>> response = new ApiResponse<>(HttpStatus.OK.value(),
                    "Get sheet mark by student id", sheetMarkDtos);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            ApiResponse<List<SheetMarkDtoForStudent>> response = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(),
                    e.getMessage(),
                    null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            ApiResponse<List<SheetMarkDtoForStudent>> response = new ApiResponse<>(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get Sheet Mark By Specification
    @GetMapping("/get-by-specification")
    public ResponseEntity<ApiResponse<List<SheetMarkDtoForStudent>>> getSheetMarkBySpecification(
            @RequestBody GetSheetMarkRequest request) {
        try {
            String id = CurrentUser.getUserId();

            List<SheetMarkDtoForStudent> sheetMarkDtos = sheetMarkDtoForStudentConverter
                    .convert(sheetMarkService.getSheetMarksBySpecification(request, id));
            ApiResponse<List<SheetMarkDtoForStudent>> response = new ApiResponse<>(HttpStatus.OK.value(),
                    "Get sheet mark by specification", sheetMarkDtos);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            ApiResponse<List<SheetMarkDtoForStudent>> response = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(),
                    e.getMessage(),
                    null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            ApiResponse<List<SheetMarkDtoForStudent>> response = new ApiResponse<>(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get-statistic")
    public ResponseEntity<ApiResponse<StudentStatisticsResponse>> getStatistic() {
        try {
            String id = CurrentUser.getUserId();

            StudentStatisticsResponse statisticsResponse = sheetMarkService.getStatistic(id);
            ApiResponse<StudentStatisticsResponse> response = new ApiResponse<>(HttpStatus.OK.value(),
                    "Get GPA", statisticsResponse);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            ApiResponse<StudentStatisticsResponse> response = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(),
                    e.getMessage(),
                    null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            ApiResponse<StudentStatisticsResponse> response = new ApiResponse<>(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
