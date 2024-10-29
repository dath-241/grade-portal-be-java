package com.hcmut.gradeportal.controller.student;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hcmut.gradeportal.dtos.sheetMark.SheetMarkDto;
import com.hcmut.gradeportal.dtos.sheetMark.SheetMarkDtoConverter;
import com.hcmut.gradeportal.dtos.sheetMark.request.GetSheetMarkRequest;
import com.hcmut.gradeportal.response.ApiResponse;
import com.hcmut.gradeportal.service.SheetMarkService;

@RestController
@RequestMapping("/student/sheetmark-info")
public class StudentSheetMarkInfoController {
    private final SheetMarkDtoConverter sheetMarkDtoConverter;

    private final SheetMarkService sheetMarkService;

    public StudentSheetMarkInfoController(SheetMarkService sheetMarkService,
            SheetMarkDtoConverter sheetMarkDtoConverter) {
        this.sheetMarkService = sheetMarkService;
        this.sheetMarkDtoConverter = sheetMarkDtoConverter;
    }

    ///////////////// All Get request for student sheet mark info /////////////////
    // Get Sheet Mark By Student Id
    @GetMapping("/get-sheetmark/{id}")
    public ResponseEntity<ApiResponse<List<SheetMarkDto>>> getSheetMarkByStudentId(@PathVariable String id) {
        try {
            List<SheetMarkDto> sheetMarkDtos = sheetMarkDtoConverter
                    .convert(sheetMarkService.getSheetMarkByStudentId(id));
            ApiResponse<List<SheetMarkDto>> response = new ApiResponse<>(HttpStatus.OK.value(),
                    "Get sheet mark by student id", sheetMarkDtos);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            ApiResponse<List<SheetMarkDto>> response = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(),
                    null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            ApiResponse<List<SheetMarkDto>> response = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get Sheet Mark By Specification
    @GetMapping("/get-by-specification")
    public ResponseEntity<ApiResponse<List<SheetMarkDto>>> getSheetMarkBySpecification(
            @RequestBody GetSheetMarkRequest request) {
        try {
            List<SheetMarkDto> sheetMarkDtos = sheetMarkDtoConverter
                    .convert(sheetMarkService.getSheetMarksBySpecification(request));
            ApiResponse<List<SheetMarkDto>> response = new ApiResponse<>(HttpStatus.OK.value(),
                    "Get sheet mark by specification", sheetMarkDtos);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            ApiResponse<List<SheetMarkDto>> response = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(),
                    null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            ApiResponse<List<SheetMarkDto>> response = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
