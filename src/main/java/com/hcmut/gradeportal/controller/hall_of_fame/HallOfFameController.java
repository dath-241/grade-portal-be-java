package com.hcmut.gradeportal.controller.hall_of_fame;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hcmut.gradeportal.dtos.hall_of_fame.GetHallOfFameRequest;
import com.hcmut.gradeportal.dtos.hall_of_fame.TopGradeForCourse;
import com.hcmut.gradeportal.response.ApiResponse;
import com.hcmut.gradeportal.service.HallOfFameService;

@RestController
@RequestMapping("/hall-of-fame")
public class HallOfFameController {
    private final HallOfFameService hallOfFameService;

    public HallOfFameController(HallOfFameService hallOfFameService) {
        this.hallOfFameService = hallOfFameService;
    }

    // Get hall of fame for course in one semester
    @GetMapping()
    public ResponseEntity<ApiResponse<TopGradeForCourse>> getHallOfFameByRequest(
            @RequestBody GetHallOfFameRequest request) {
        try {
            TopGradeForCourse topGradeForCourse = hallOfFameService.getHallOfFameForCourse(request);
            ApiResponse<TopGradeForCourse> response = new ApiResponse<>(HttpStatus.OK.value(),
                    "Get hall of fame for course" + request.getCourseCode() + " in semester "
                            + request.getSemesterCode(),
                    topGradeForCourse);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            ApiResponse<TopGradeForCourse> response = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(),
                    null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            ApiResponse<TopGradeForCourse> response = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get-all")
    public ResponseEntity<ApiResponse<List<TopGradeForCourse>>> getAllHallOfFame(
            @RequestBody GetHallOfFameRequest request) {
        try {
            List<TopGradeForCourse> topGradeForCourses = hallOfFameService.getAllHallOfFame(request);
            ApiResponse<List<TopGradeForCourse>> response = new ApiResponse<>(HttpStatus.OK.value(),
                    "Get all hall of fame", topGradeForCourses);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            ApiResponse<List<TopGradeForCourse>> response = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(),
                    e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            ApiResponse<List<TopGradeForCourse>> response = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
