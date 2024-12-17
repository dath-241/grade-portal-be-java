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
import com.hcmut.gradeportal.dtos.sheetMark.request.UpdateSheetMarkWithSheetMarkIdRequest;
import com.hcmut.gradeportal.dtos.sheetMark.request.UpdateSheetMarkWithStudentIdRequest;
import com.hcmut.gradeportal.dtos.sheetMark.response.UpdateBulkSheetMarkResponse;
import com.hcmut.gradeportal.response.ApiResponse;
import com.hcmut.gradeportal.security.utils.CurrentUser;
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

    // Update Sheet Mark with student ID
    @PutMapping("/update/student-id")
    public ResponseEntity<ApiResponse<SheetMarkDto>> updateSheetMarkWithStudentId(
            @RequestBody UpdateSheetMarkWithStudentIdRequest request) {
        try {
            String userId = CurrentUser.getUserId();

            SheetMarkDto sheetMarkDto = sheetMarkDtoConverter
                    .convert(sheetMarkService.updateSheetMark(request, userId));
            ApiResponse<SheetMarkDto> response = new ApiResponse<>(HttpStatus.OK.value(),
                    "Sheet mark updated successfully", sheetMarkDto);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null),
                    HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Update Sheet Mark with SheetMark ID
    @PutMapping("/update/sheetmark-id")
    public ResponseEntity<ApiResponse<SheetMarkDto>> updateSheetMarkWithSheetMarkId(
            @RequestBody UpdateSheetMarkWithSheetMarkIdRequest request) {
        try {
            String userId = CurrentUser.getUserId();

            SheetMarkDto sheetMarkDto = sheetMarkDtoConverter
                    .convert(sheetMarkService.updateSheetMark(request, userId));
            ApiResponse<SheetMarkDto> response = new ApiResponse<>(HttpStatus.OK.value(),
                    "Sheet mark updated successfully", sheetMarkDto);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null),
                    HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Bulk Update Sheet Mark with student ID
    @PutMapping("/bulk-update/student-id")
    public ResponseEntity<ApiResponse<List<UpdateBulkSheetMarkResponse>>> bulkUpdateSheetMarkWithStudentId(
            @RequestBody List<UpdateSheetMarkWithStudentIdRequest> requests) {
        try {
            String userId = CurrentUser.getUserId();
            List<UpdateBulkSheetMarkResponse> responses = new ArrayList<>();
            for (UpdateSheetMarkWithStudentIdRequest request : requests) {
                try {
                    SheetMarkDto sheetMarkDto = sheetMarkDtoConverter
                            .convert(sheetMarkService.updateSheetMark(request, userId));
                    responses.add(new UpdateBulkSheetMarkResponse(sheetMarkDto.studentEmail(), HttpStatus.OK.value(),
                            "Update successful"));
                } catch (IllegalArgumentException e) {
                    responses
                            .add(new UpdateBulkSheetMarkResponse(null, HttpStatus.BAD_REQUEST.value(), e.getMessage()));
                }
            }
            ApiResponse<List<UpdateBulkSheetMarkResponse>> response = new ApiResponse<>(HttpStatus.OK.value(),
                    "Bulk sheet mark update successful", responses);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Bulk Update Sheet Mark with SheetMark ID
    @PutMapping("/bulk-update/sheetmark-id")
    public ResponseEntity<ApiResponse<List<UpdateBulkSheetMarkResponse>>> bulkUpdateSheetMarkWithSheetMarkId(
            @RequestBody List<UpdateSheetMarkWithSheetMarkIdRequest> requests) {
        try {
            String userId = CurrentUser.getUserId();
            List<UpdateBulkSheetMarkResponse> responses = new ArrayList<>();
            for (UpdateSheetMarkWithSheetMarkIdRequest request : requests) {
                try {
                    SheetMarkDto sheetMarkDto = sheetMarkDtoConverter
                            .convert(sheetMarkService.updateSheetMark(request, userId));
                    responses.add(new UpdateBulkSheetMarkResponse(sheetMarkDto.studentEmail(), HttpStatus.OK.value(),
                            "Update successful"));
                } catch (IllegalArgumentException e) {
                    responses
                            .add(new UpdateBulkSheetMarkResponse(null, HttpStatus.BAD_REQUEST.value(), e.getMessage()));
                }
            }
            ApiResponse<List<UpdateBulkSheetMarkResponse>> response = new ApiResponse<>(HttpStatus.OK.value(),
                    "Bulk sheet mark update successful", responses);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
