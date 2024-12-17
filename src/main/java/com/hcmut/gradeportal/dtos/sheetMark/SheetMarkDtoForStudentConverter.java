package com.hcmut.gradeportal.dtos.sheetMark;

import java.util.List;

import org.springframework.stereotype.Component;

import com.hcmut.gradeportal.dtos.course.CourseDtoConverter;
import com.hcmut.gradeportal.dtos.teacher.TeacherDtoForStudentConverter;
import com.hcmut.gradeportal.entities.SheetMark;

@Component
public class SheetMarkDtoForStudentConverter {
    private final CourseDtoConverter courseDtoConverter;
    private final TeacherDtoForStudentConverter teacherDtoForStudentConverter;

    public SheetMarkDtoForStudentConverter(CourseDtoConverter courseDtoConverter,
            TeacherDtoForStudentConverter teacherDtoForStudentConverter) {
        this.courseDtoConverter = courseDtoConverter;
        this.teacherDtoForStudentConverter = teacherDtoForStudentConverter;
    }

    public SheetMarkDtoForStudent convert(SheetMark from) {
        return new SheetMarkDtoForStudent(
                courseDtoConverter.convert(from.getCourseClass().getCourse()),
                from.getCourseClass().getId().getSemesterCode(),
                from.getCourseClass().getId().getClassName(),
                teacherDtoForStudentConverter.convert(from.getTeacher()),
                from.getBT(),
                from.getTN(),
                from.getBTL(),
                from.getGK(),
                from.getCK(),
                from.getFinalMark(),
                from.getCreatedAt(),
                from.getUpdatedAt(),
                from.getSheetMarkStatus());
    }

    public List<SheetMarkDtoForStudent> convert(List<SheetMark> from) {
        return from.stream().map(this::convert).toList();
    }
}
