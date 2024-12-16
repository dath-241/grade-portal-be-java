package com.hcmut.gradeportal.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.hcmut.gradeportal.dtos.hall_of_fame.GetAllHallOfFameRequest;
import com.hcmut.gradeportal.dtos.hall_of_fame.GetHallOfFameRequest;
import com.hcmut.gradeportal.dtos.hall_of_fame.TopGradeForCourse;
import com.hcmut.gradeportal.dtos.sheetMark.SheetMarkDtoForHallOfFame;
import com.hcmut.gradeportal.dtos.sheetMark.SheetMarkDtoForHallOfFameConverter;
import com.hcmut.gradeportal.entities.Course;
import com.hcmut.gradeportal.entities.CourseClass;
import com.hcmut.gradeportal.entities.Semester;
import com.hcmut.gradeportal.entities.SheetMark;
import com.hcmut.gradeportal.repositories.CourseClassRepository;
import com.hcmut.gradeportal.repositories.CourseRepository;
import com.hcmut.gradeportal.repositories.SemesterRepository;
import com.hcmut.gradeportal.repositories.SheetMarkRepository;
import com.hcmut.gradeportal.specification.CourseClassSpecification;

@Service
public class HallOfFameService {
    private final CourseRepository courseRepository;
    private final CourseClassRepository courseClassRepository;
    private final SemesterRepository semesterRepository;
    private final SheetMarkRepository sheetMarkRepository;

    private final SheetMarkDtoForHallOfFameConverter sheetMarkDtoForHallOfFameConverter;

    public HallOfFameService(CourseRepository courseRepository, CourseClassRepository courseClassRepository,
            SemesterRepository semesterRepository, SheetMarkRepository sheetMarkRepository,
            SheetMarkDtoForHallOfFameConverter sheetMarkDtoForHallOfFameConverter) {
        this.courseRepository = courseRepository;
        this.courseClassRepository = courseClassRepository;
        this.semesterRepository = semesterRepository;
        this.sheetMarkRepository = sheetMarkRepository;
        this.sheetMarkDtoForHallOfFameConverter = sheetMarkDtoForHallOfFameConverter;
    }

    // Get hall of fame for one course
    public TopGradeForCourse getHallOfFameForCourse(GetHallOfFameRequest request) {
        try {
            System.out.println("Get hall of fame for course " + request.getCourseCode() + " in semester "
                    + request.getSemesterCode());

            String semeterCode = request.getSemesterCode();
            String courseCode = request.getCourseCode();
            Integer noOfRanks = (request.getNoOfRanks() == null) ? 10 : request.getNoOfRanks();

            // Xử lý lỗi khi không tìm thấy Course hoặc Semester
            Course course = courseRepository.findByCourseCode(courseCode)
                    .orElseThrow(() -> new IllegalArgumentException("Course not found with code: " + courseCode));

            Semester semester = semesterRepository.findBySemesterCode(semeterCode)
                    .orElseThrow(() -> new IllegalArgumentException("Semester not found with code: " + semeterCode));

            // Tìm các SheetMark liên quan
            List<SheetMark> sheetMarks = sheetMarkRepository
                    .findByCourseCodeAndSemesterCodeAndFinalMarkNotNull(courseCode, semester.getSemesterCode());

            System.out.println("SheetMarks: " + sheetMarks.size());

            if (sheetMarks.isEmpty()) {
                throw new IllegalArgumentException(
                        "No sheet marks found for course " + courseCode + " in semester " + semeterCode);
            }

            // Sắp xếp và lấy danh sách top sinh viên
            sheetMarks.sort((a, b) -> Double.compare(b.getFinalMark(), a.getFinalMark()));
            int count = 0;
            List<SheetMarkDtoForHallOfFame> result = new ArrayList<>();

            for (int i = 0; i < sheetMarks.size(); i++) {
                SheetMark current = sheetMarks.get(i);
                result.add(sheetMarkDtoForHallOfFameConverter.convert(current));

                if (i == 0 || !sheetMarks.get(i).getFinalMark().equals(sheetMarks.get(i - 1).getFinalMark())) {
                    count++;
                }

                if (count == noOfRanks && (i + 1 == sheetMarks.size()
                        || !current.getFinalMark().equals(sheetMarks.get(i + 1).getFinalMark()))) {
                    break;
                }
            }

            return new TopGradeForCourse(course.getCourseCode(), course.getCourseName(), semester.getSemesterCode(),
                    semester.getSemesterName(), result);
        } catch (IllegalArgumentException e) {
            throw e; // Ném lại lỗi để Controller xử lý
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error while fetching hall of fame for course", e);
        }
    }

    // Get all hall of fame of all course in semester
    public List<TopGradeForCourse> getAllHallOfFame(GetAllHallOfFameRequest request) {
        List<TopGradeForCourse> result = new ArrayList<>();
        Semester semester = semesterRepository.findBySemesterCode(request.getSemesterCode())
                .orElseThrow(() -> new IllegalArgumentException("Semester not found"));

        List<String> courseCodes = new ArrayList<>();
        Specification<CourseClass> spec = Specification
                .where(CourseClassSpecification.hasSemesterCode(semester.getSemesterCode()));

        List<CourseClass> courseClasses = courseClassRepository.findAll(spec);
        for (CourseClass courseClass : courseClasses) {
            if (!courseCodes.contains(courseClass.getId().getCourseCode())) {
                courseCodes.add(courseClass.getId().getCourseCode());
            }
        }

        for (String courseCode : courseCodes) {
            GetHallOfFameRequest getHallOfFameRequest = new GetHallOfFameRequest(courseCode, semester.getSemesterCode(),
                    10);
            result.add(getHallOfFameForCourse(getHallOfFameRequest));
        }

        return result;
    }
}