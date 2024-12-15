package com.hcmut.gradeportal.dtos.sheetMark;

import org.springframework.stereotype.Component;

import com.hcmut.gradeportal.dtos.student.StudentDtoForHallOfFameConverter;
import com.hcmut.gradeportal.dtos.teacher.TeacherDtoForHallOfFameConverter;
import com.hcmut.gradeportal.entities.SheetMark;

@Component
public class SheetMarkDtoForHallOfFameConverter {
    private final StudentDtoForHallOfFameConverter studentDtoForHallOfFameConverter;
    private final TeacherDtoForHallOfFameConverter teacherDtoForHallOfFameConverter;

    public SheetMarkDtoForHallOfFameConverter(StudentDtoForHallOfFameConverter studentDtoForHallOfFameConverter,
            TeacherDtoForHallOfFameConverter teacherDtoForHallOfFameConverter) {
        this.studentDtoForHallOfFameConverter = studentDtoForHallOfFameConverter;
        this.teacherDtoForHallOfFameConverter = teacherDtoForHallOfFameConverter;
    }

    public SheetMarkDtoForHallOfFame convert(SheetMark from) {
        return new SheetMarkDtoForHallOfFame(
                studentDtoForHallOfFameConverter.convert(from.getStudent()),
                teacherDtoForHallOfFameConverter.convert(from.getTeacher()),
                from.getCourseClass().getId().getClassName(),
                from.getCourseClass().getClassStatus(),
                from.getBT(),
                from.getTN(),
                from.getBTL(),
                from.getGK(),
                from.getCK(),
                from.getFinalMark(),
                from.getSheetMarkStatus());
    }
}
