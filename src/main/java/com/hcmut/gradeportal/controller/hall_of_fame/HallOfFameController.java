package com.hcmut.gradeportal.controller.hall_of_fame;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hcmut.gradeportal.dtos.halloffame.HallOfFameRequest;
import com.hcmut.gradeportal.entities.HallOfFame;
import com.hcmut.gradeportal.response.ApiResponse;
import com.hcmut.gradeportal.service.HallOfFameService;

@RestController
@RequestMapping("/hall-of-fame")
public class HallOfFameController {
    private final HallOfFameService hallOfFameService;

    public HallOfFameController(HallOfFameService hallOfFameService) {
        this.hallOfFameService = hallOfFameService;
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<List<HallOfFame>>> getAllClasses(@RequestBody HallOfFameRequest request) {
        try {
            List<HallOfFame> courseClassDtos = hallOfFameService.halloffame(request);
            ApiResponse<List<HallOfFame>> response = new ApiResponse<>(HttpStatus.OK.value(), "Get all classes",
                    courseClassDtos);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse<List<HallOfFame>> response = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
