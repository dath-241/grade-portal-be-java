package com.hcmut.gradeportal.dtos.semester;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.hcmut.gradeportal.entities.Semester;

@Component
public class SemesterDtoConverter {
    public SemesterDtoConverter() {
    }

    public SemesterDto convert(Semester from) {
        return new SemesterDto(
                from.getSemesterCode(),
                from.getSemesterName(),
                from.getSemesterDuration());
    }

    public List<SemesterDto> convert(List<Semester> from) {
        return from.stream().map(this::convert).collect(Collectors.toList());
    }

    public Optional<SemesterDto> convert(Optional<Semester> from) {
        return from.map(this::convert);
    }
}
