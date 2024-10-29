package com.hcmut.gradeportal.controller.teacher;

import java.util.List;
import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hcmut.gradeportal.dtos.sheetMark.SheetMarkDto;
import com.hcmut.gradeportal.dtos.sheetMark.SheetMarkDtoConverter;
import com.hcmut.gradeportal.dtos.sheetMark.request.UpdateSheetMarkWithEmailRequest;
import com.hcmut.gradeportal.dtos.sheetMark.request.UpdateSheetMarkWithStudentIdRequest;
import com.hcmut.gradeportal.dtos.sheetMark.response.UpdateBulkSheetMarkResponse;
import com.hcmut.gradeportal.response.ApiResponse;
import com.hcmut.gradeportal.service.SheetMarkService;

@RestController
@RequestMapping("/teacher/manage-sheetmark")
public class TeacherManageSheetMarkController {
    private final SheetMarkDtoConverter sheetMarkDtoConverter;
    private final SheetMarkService sheetMarkService;

    public TeacherManageSheetMarkController(SheetMarkService sheetMarkService,
            SheetMarkDtoConverter sheetMarkDtoConverter) {
        this.sheetMarkService = sheetMarkService;
        this.sheetMarkDtoConverter = sheetMarkDtoConverter;
    }

    ///////////////// All Get request for teacher manage sheet mark
    ///////////////// /////////////////

    ///////////////////// All Post request for teacher manage sheet mark
    ///////////////////// /////////////////////

    ///////////////////// All Put request for teacher manage sheet mark
    ///////////////////// /////////////////////
    // Update Sheet Mark with student id
    @PutMapping("/update-with-student-id")
    public ResponseEntity<ApiResponse<SheetMarkDto>> updateSheetMark(
            @RequestBody UpdateSheetMarkWithStudentIdRequest request) {
        try {
            SheetMarkDto sheetMarkDto = sheetMarkDtoConverter.convert(sheetMarkService.updateSheetMark(request));
            ApiResponse<SheetMarkDto> response = new ApiResponse<>(HttpStatus.OK.value(), "Update sheet mark",
                    sheetMarkDto);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            ApiResponse<SheetMarkDto> response = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(),
                    null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            ApiResponse<SheetMarkDto> response = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    e.getMessage(),
                    null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Update Sheet Mark with student email
    @PutMapping("/update-with-student-email")
    public ResponseEntity<ApiResponse<SheetMarkDto>> updateSheetMark(
            @RequestBody UpdateSheetMarkWithEmailRequest request) {
        try {
            SheetMarkDto sheetMarkDto = sheetMarkDtoConverter.convert(sheetMarkService.updateSheetMark(request));
            ApiResponse<SheetMarkDto> response = new ApiResponse<>(HttpStatus.OK.value(), "Update sheet mark",
                    sheetMarkDto);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            ApiResponse<SheetMarkDto> response = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(),
                    null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            ApiResponse<SheetMarkDto> response = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    e.getMessage(),
                    null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Update Bulk Sheet Mark with student id
    @PutMapping("/update-bulk-with-student-id")
    public ResponseEntity<ApiResponse<List<UpdateBulkSheetMarkResponse>>> updateBulkSheetMark(
            @RequestBody List<UpdateSheetMarkWithStudentIdRequest> requests) {
        try {
            List<UpdateBulkSheetMarkResponse> responses = new ArrayList<>();

            for (UpdateSheetMarkWithStudentIdRequest request : requests) {
                try {
                    SheetMarkDto sheetMarkDto = sheetMarkDtoConverter
                            .convert(sheetMarkService.updateSheetMark(request));
                    responses.add(new UpdateBulkSheetMarkResponse(sheetMarkDto.studentEmail(), HttpStatus.OK.value(),
                            "Update sheet mark"));
                } catch (IllegalArgumentException e) {
                    responses
                            .add(new UpdateBulkSheetMarkResponse(null, HttpStatus.BAD_REQUEST.value(), e.getMessage()));
                } catch (Exception e) {
                    responses.add(new UpdateBulkSheetMarkResponse(null, HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            e.getMessage()));
                }
            }

            ApiResponse<List<UpdateBulkSheetMarkResponse>> response = new ApiResponse<>(HttpStatus.OK.value(),
                    "Update bulk sheet mark",
                    responses);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            ApiResponse<List<UpdateBulkSheetMarkResponse>> response = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(),
                    e.getMessage(),
                    null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            ApiResponse<List<UpdateBulkSheetMarkResponse>> response = new ApiResponse<>(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    e.getMessage(),
                    null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Update Bulk Sheet Mark with student email
    @PutMapping("/update-bulk-with-student-email")
    public ResponseEntity<ApiResponse<List<UpdateBulkSheetMarkResponse>>> updateBulkSheetMarkWithEmailStudent(
            @RequestBody List<UpdateSheetMarkWithEmailRequest> requests) {
        try {
            List<UpdateBulkSheetMarkResponse> responses = new ArrayList<>();

            for (UpdateSheetMarkWithEmailRequest request : requests) {
                try {
                    SheetMarkDto sheetMarkDto = sheetMarkDtoConverter
                            .convert(sheetMarkService.updateSheetMark(request));
                    responses.add(new UpdateBulkSheetMarkResponse(sheetMarkDto.studentEmail(), HttpStatus.OK.value(),
                            "Update sheet mark"));
                } catch (IllegalArgumentException e) {
                    responses
                            .add(new UpdateBulkSheetMarkResponse(null, HttpStatus.BAD_REQUEST.value(), e.getMessage()));
                } catch (Exception e) {
                    responses.add(new UpdateBulkSheetMarkResponse(null, HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            e.getMessage()));
                }
            }

            ApiResponse<List<UpdateBulkSheetMarkResponse>> response = new ApiResponse<>(HttpStatus.OK.value(),
                    "Update bulk sheet mark",
                    responses);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            ApiResponse<List<UpdateBulkSheetMarkResponse>> response = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(),
                    e.getMessage(),
                    null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            ApiResponse<List<UpdateBulkSheetMarkResponse>> response = new ApiResponse<>(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    e.getMessage(),
                    null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    ///////////////////// All Delete request for teacher manage sheet mark
    ///////////////////// /////////////////////
}
