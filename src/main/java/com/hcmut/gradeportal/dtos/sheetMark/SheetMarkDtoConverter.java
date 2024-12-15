package com.hcmut.gradeportal.dtos.sheetMark;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.hcmut.gradeportal.entities.SheetMark;

@Component
public class SheetMarkDtoConverter {

    public SheetMarkDtoConverter() {

    }

    public SheetMarkDto convert(SheetMark from) {
        return new SheetMarkDto(from.getId(), from.getStudent().getId(), from.getStudent().getEmail(),
                from.getTeacher().getId(),
                from.getCourseClass().getId().getCourseCode(), from.getCourseClass().getId().getSemesterCode(),
                from.getCourseClass().getId().getClassName(), from.getCreatedAt(), from.getUpdatedAt(), from.getBT(),
                from.getTN(), from.getBTL(), from.getGK(), from.getCK(), from.getFinalMark(),
                from.getSheetMarkStatus());
    }

    public List<SheetMarkDto> convert(List<SheetMark> from) {
        return from.stream().map(this::convert).collect(Collectors.toList());
    }

    public Optional<SheetMarkDto> convert(Optional<SheetMark> from) {
        return from.map(this::convert);
    }
}
