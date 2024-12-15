package com.hcmut.gradeportal.controller.init_data;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hcmut.gradeportal.config.DataInitializer;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import com.hcmut.gradeportal.response.ApiResponse;

@RestController
@RequestMapping("/init-data")
public class InitDataController {

    private final DataInitializer dataInitializer;

    public InitDataController(DataInitializer dataInitializer) {
        this.dataInitializer = dataInitializer;
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<String>> initializeCourseClassesAndSheetMarks() {
        try {
            System.out.println("Starting CourseClasses and SheetMarks initialization...");
            dataInitializer.initializeCourseClassesAndSheetMarks();
            return ResponseEntity
                    .ok(new ApiResponse<>(200, "Course classes and sheet marks initialization completed.", null));
        } catch (Exception e) {
            System.err.println("Failed to initialize course classes or sheet marks: " + e.getMessage());
            return ResponseEntity.status(500)
                    .body(new ApiResponse<>(500, "Failed to initialize course classes or sheet marks.", null));
        }
    }
}
