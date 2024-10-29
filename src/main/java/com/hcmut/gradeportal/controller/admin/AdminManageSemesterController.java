package com.hcmut.gradeportal.controller.admin;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hcmut.gradeportal.dtos.semester.SemesterDto;
import com.hcmut.gradeportal.dtos.semester.SemesterDtoConverter;
import com.hcmut.gradeportal.dtos.semester.request.CreateSemesterBySemesterCode;
import com.hcmut.gradeportal.dtos.semester.response.CreateBulkSemesterResponse;
import com.hcmut.gradeportal.response.ApiResponse;
import com.hcmut.gradeportal.service.SemesterService;

@RestController
@RequestMapping("/admin/manage-semester")
public class AdminManageSemesterController {
    private final SemesterService semesterService;
    private final SemesterDtoConverter semesterDtoConverter;

    public AdminManageSemesterController(SemesterService semesterService, SemesterDtoConverter semesterDtoConverter) {
        this.semesterService = semesterService;
        this.semesterDtoConverter = semesterDtoConverter;
    }

    ///////////////// All Get request for manage semester /////////////////
    // Get all semesters
    @GetMapping("/get-all")
    public ResponseEntity<ApiResponse<List<SemesterDto>>> getAllSemesters() {
        try {
            List<SemesterDto> semesterDtos = semesterDtoConverter.convert(semesterService.getAllSemesters());
            ApiResponse<List<SemesterDto>> response = new ApiResponse<>(HttpStatus.OK.value(), "Get all semesters",
                    semesterDtos);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse<List<SemesterDto>> response = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get semester by semester code
    @GetMapping("/get-by-semester-code/{semesterCode}")
    public ResponseEntity<ApiResponse<SemesterDto>> getSemesterBySemesterCode(@PathVariable String semesterCode) {
        try {
            SemesterDto semesterDto = semesterDtoConverter
                    .convert(semesterService.getSemesterBySemesterCode(semesterCode));
            ApiResponse<SemesterDto> response = new ApiResponse<>(HttpStatus.OK.value(),
                    "Get semester by semester code",
                    semesterDto);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            ApiResponse<SemesterDto> response = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            ApiResponse<SemesterDto> response = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get current semester
    @GetMapping("/get-current-semester")
    public ResponseEntity<ApiResponse<SemesterDto>> getCurrentSemester() {
        try {
            SemesterDto semesterDto = semesterDtoConverter.convert(semesterService.getCurrentSemester());
            ApiResponse<SemesterDto> response = new ApiResponse<>(HttpStatus.OK.value(), "Get current semester",
                    semesterDto);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            ApiResponse<SemesterDto> response = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            ApiResponse<SemesterDto> response = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get recommended list of semesters
    @GetMapping("/get-recommended-list-semesters")
    public ResponseEntity<ApiResponse<List<SemesterDto>>> getRecommendedListSemester() {
        try {
            List<SemesterDto> res = semesterDtoConverter.convert(semesterService.createSemesterBaseOnRecommend());
            ApiResponse<List<SemesterDto>> response = new ApiResponse<>(HttpStatus.OK.value(),
                    "Get recommended list of semesters",
                    res);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            ApiResponse<List<SemesterDto>> response = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(),
                    null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            ApiResponse<List<SemesterDto>> response = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    ///////////////// All Post request for manage semester /////////////////
    // create semester
    @PostMapping("/create-semester")
    public ResponseEntity<ApiResponse<SemesterDto>> createSemester(@RequestBody CreateSemesterBySemesterCode request) {
        try {
            SemesterDto semesterDto = semesterDtoConverter
                    .convert(semesterService.createSemester(request.getSemesterCode()));
            ApiResponse<SemesterDto> response = new ApiResponse<>(HttpStatus.OK.value(), "Create semester",
                    semesterDto);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            ApiResponse<SemesterDto> response = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            ApiResponse<SemesterDto> response = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // create list of semesters
    @PostMapping("/create-list-semesters")
    public ResponseEntity<ApiResponse<List<CreateBulkSemesterResponse>>> createListSemesters(
            @RequestBody List<CreateSemesterBySemesterCode> requests) {
        try {
            List<CreateBulkSemesterResponse> responses = new ArrayList<>();
            for (CreateSemesterBySemesterCode request : requests) {
                try {
                    SemesterDto semesterDto = semesterDtoConverter
                            .convert(semesterService.createSemester(request.getSemesterCode()));
                    responses.add(new CreateBulkSemesterResponse(semesterDto.semesterCode(), HttpStatus.OK.value(),
                            "Create semester"));
                } catch (IllegalArgumentException e) {
                    responses.add(
                            new CreateBulkSemesterResponse(request.getSemesterCode(), HttpStatus.BAD_REQUEST.value(),
                                    e.getMessage()));
                }
            }

            ApiResponse<List<CreateBulkSemesterResponse>> response = new ApiResponse<>(HttpStatus.OK.value(),
                    "Create list of semesters", responses);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse<List<CreateBulkSemesterResponse>> response = new ApiResponse<>(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Create recommended semester
    @PostMapping("/create-recommended-semester")
    public ResponseEntity<ApiResponse<List<SemesterDto>>> createRecommendedSemester() {
        try {
            List<SemesterDto> res = semesterDtoConverter.convert(semesterService.createSemesterBaseOnRecommend());
            ApiResponse<List<SemesterDto>> response = new ApiResponse<>(HttpStatus.OK.value(),
                    "Create recommended semester",
                    res);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            ApiResponse<List<SemesterDto>> response = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(),
                    null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            ApiResponse<List<SemesterDto>> response = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    ///////////////// All Put request for manage semester /////////////////

    ///////////////// All Delete request for manage semester /////////////////
}
