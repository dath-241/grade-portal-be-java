package com.hcmut.gradeportal.service;

import org.springframework.stereotype.Service;

import com.hcmut.gradeportal.dtos.semester.request.CreateSemesterRequest;
import com.hcmut.gradeportal.entities.Semester;
import com.hcmut.gradeportal.repositories.SemesterRepository;

@Service
public class SemesterService {
    private final SemesterRepository semesterRepository;

    public SemesterService(SemesterRepository semesterRepository) {
        this.semesterRepository = semesterRepository;
    }

    public Semester createSemester(CreateSemesterRequest createSemesterRequest) {
        Semester semester = new Semester();
        semester.setSemesterCode(createSemesterRequest.getSemesterCode());
        semester.setSemesterName(createSemesterRequest.getSemesterName());
        semester.setSemesterDuration(createSemesterRequest.getSemesterDuration());
        return semesterRepository.save(semester);
    }
}
