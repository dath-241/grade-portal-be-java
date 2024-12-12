package com.hcmut.gradeportal.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.hcmut.gradeportal.dtos.halloffame.HallOfFameRequest;
import com.hcmut.gradeportal.entities.CourseClass;
import com.hcmut.gradeportal.entities.HallOfFame;
import com.hcmut.gradeportal.entities.SheetMark;
import com.hcmut.gradeportal.entities.Student;
import com.hcmut.gradeportal.entities.enums.ClassStatus;
import com.hcmut.gradeportal.repositories.CourseClassRepository;
import com.hcmut.gradeportal.repositories.SheetMarkRepository;
import com.hcmut.gradeportal.specification.CourseClassSpecification;

@Service
public class HallOfFameService {
    private final CourseClassRepository courseClassRepository;
    private final SheetMarkRepository sheetMarkRepository;

    public HallOfFameService(CourseClassRepository courseClassRepository, SheetMarkRepository sheetMarkRepository) {
        this.courseClassRepository = courseClassRepository;
        this.sheetMarkRepository = sheetMarkRepository;
    }

    public List<HallOfFame> halloffame(HallOfFameRequest request) {
        List<HallOfFame> temp = new ArrayList<>();
        Specification<CourseClass> spec = Specification
                .where(CourseClassSpecification.hasCourseCode(request.getCourseCode()))
                .and(CourseClassSpecification.hasSemesterCode(request.getSemesterCode()))
                .and(CourseClassSpecification.hasClassStatus(ClassStatus.Completed));

        // Truy vấn dữ liệu từ database
        List<CourseClass> listClass = courseClassRepository.findAll(spec);
        for (CourseClass courseclass : listClass) {
            for (Student student : courseclass.getListOfStudents()) {
                Optional<SheetMark> sheetmark = sheetMarkRepository
                        .findByStudentIdAndCourseCodeAndSemesterCodeAndClassName(
                                student.getId(), courseclass.getCourseCode(), courseclass.getSemesterCode(),
                                courseclass.getClassName());

                HallOfFame hallOfFame = new HallOfFame(student, sheetmark.get().getBT(),
                        sheetmark.get().getTN(), sheetmark.get().getBTL(), sheetmark.get().getGK(),
                        sheetmark.get().getCK(), sheetmark.get().getFinalMark());
                int i = 0;
                for (; i < temp.size(); i++) {
                    if (hallOfFame.getFinalMark() > temp.get(i).getFinalMark())
                        break;
                }
                temp.add(i, hallOfFame);

            }
        }
        int a = request.getNoOfStudents();
        int i = a;
        double epsilon = 1e-9;

        if (i > temp.size()) {
            return temp;
        } else {
            List<HallOfFame> result;
            while (Math.abs(temp.get(a - 1).getFinalMark() - temp.get(i - 1).getFinalMark()) < epsilon
                    && i <= temp.size()) {
                i++;
                if (i > temp.size())
                    break;
            }

            result = temp.subList(0, i - 1);
            return result;
        }

    }
}
