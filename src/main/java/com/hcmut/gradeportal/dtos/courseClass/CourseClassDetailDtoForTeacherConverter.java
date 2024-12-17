package com.hcmut.gradeportal.dtos.courseClass;

import java.util.List;

import org.springframework.stereotype.Component;

import com.hcmut.gradeportal.dtos.course.CourseDtoForTeacherConverter;
import com.hcmut.gradeportal.dtos.sheetMark.SheetMarkDtoForTeacherConverter;
import com.hcmut.gradeportal.entities.CourseClass;
import com.hcmut.gradeportal.entities.SheetMark;
import com.hcmut.gradeportal.repositories.SheetMarkRepository;

@Component
public class CourseClassDetailDtoForTeacherConverter {
    private final CourseDtoForTeacherConverter courseDtoForTeacherConverter;
    private final SheetMarkDtoForTeacherConverter sheetMarkDtoForTeacherConverter;

    private final SheetMarkRepository sheetMarkRepository;

    public CourseClassDetailDtoForTeacherConverter(CourseDtoForTeacherConverter courseDtoForTeacherConverter,
            SheetMarkDtoForTeacherConverter sheetMarkDtoForTeacherConverter, SheetMarkRepository sheetMarkRepository) {
        this.courseDtoForTeacherConverter = courseDtoForTeacherConverter;
        this.sheetMarkDtoForTeacherConverter = sheetMarkDtoForTeacherConverter;
        this.sheetMarkRepository = sheetMarkRepository;
    }

    public CourseClassDetailDtoForTeacher convert(CourseClass from) {
        List<SheetMark> sheetMarks = sheetMarkRepository.findByCourseCodeAndSemesterCodeAndClassName(
                from.getId().getCourseCode(), from.getId().getSemesterCode(), from.getId().getClassName());

        return new CourseClassDetailDtoForTeacher(
                courseDtoForTeacherConverter.convert(from.getCourse()),
                from.getId().getSemesterCode(),
                from.getId().getClassName(),
                sheetMarkDtoForTeacherConverter.convert(sheetMarks),
                from.getCreatedAt(),
                from.getUpdatedAt(),
                from.getClassStatus());
    }
}
