package com.hcmut.gradeportal.dtos.sheetMark;

import java.util.List;
import java.util.stream.Collectors;

import com.hcmut.gradeportal.dtos.student.StudentDtoForTeacherConverter;
import com.hcmut.gradeportal.dtos.teacher.TeacherDtoForTeacherConverter;
import com.hcmut.gradeportal.entities.SheetMark;

import org.springframework.stereotype.Component;

@Component
public class SheetMarkDtoForTeacherConverter {
    private final StudentDtoForTeacherConverter studentDtoForTeacherConverter;
    private final TeacherDtoForTeacherConverter teacherDtoForTeacherConverter;

    public SheetMarkDtoForTeacherConverter(StudentDtoForTeacherConverter studentDtoForTeacherConverter,
            TeacherDtoForTeacherConverter teacherDtoForTeacherConverter) {
        this.studentDtoForTeacherConverter = studentDtoForTeacherConverter;
        this.teacherDtoForTeacherConverter = teacherDtoForTeacherConverter;
    }

    public SheetMarkDtoForTeacher convert(SheetMark from) {
        return new SheetMarkDtoForTeacher(
                from.getId(),
                studentDtoForTeacherConverter.convert(from.getStudent()),
                teacherDtoForTeacherConverter.convert(from.getTeacher()),
                from.getCourseClass().getId().getClassName(),
                from.getCourseClass().getClassStatus(),
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

    public List<SheetMarkDtoForTeacher> convert(List<SheetMark> from) {
        return from.stream().map(this::convert).collect(Collectors.toList());
    }
}
