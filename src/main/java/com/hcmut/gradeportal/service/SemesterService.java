package com.hcmut.gradeportal.service;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

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

    public Boolean isSemesterCodeValid(String semesterCode) {
        if (semesterCode.substring(0, 2).equals("HK") == false) {
            throw new IllegalArgumentException("Semester code must start with 'HK'");
        }

        if (semesterCode.length() != 5) {
            throw new IllegalArgumentException("Semester code must have 5 characters, start with 'HK' and 3 digits");
        }

        for (int i = 2; i < 5; i++) {
            if (Character.isDigit(semesterCode.charAt(i)) == false) {
                throw new IllegalArgumentException(
                        "Semester code must have 5 characters, start with 'HK' and 3 digits");
            }
        }

        return true;
    }

    public String generateSemesterCodeBaseOnRealTime() {
        // Get current year
        Integer year = java.time.LocalDate.now().getYear();

        // Get current month
        Integer month = java.time.LocalDate.now().getMonthValue();

        // Get current semester
        String semesterCode = "";
        if (month >= 1 && month <= 5) {
            semesterCode = "2";
        } else if (month >= 9 && month <= 12) {
            semesterCode = "1";
        } else {
            semesterCode = "3";
        }

        return "HK" + year.toString().substring(2) + semesterCode;
    }

    public String findNextSemesterCode() {
        String currentSemesterCode = generateSemesterCodeBaseOnRealTime();
        String nextSemesterCode = "";

        if (currentSemesterCode.substring(4).equals("1")) {
            nextSemesterCode = "HK" + currentSemesterCode.substring(2, 4) + "2";
        } else if (currentSemesterCode.substring(4).equals("2")) {
            nextSemesterCode = "HK" + (Integer.parseInt(currentSemesterCode.substring(2, 4))) + "3";
        } else {
            nextSemesterCode = "HK" + (Integer.parseInt(currentSemesterCode.substring(2, 4)) + 1) + "1";
        }

        return nextSemesterCode;
    }

    ////////////// Service for get method - read data //////////////
    // Get all semesters
    public List<Semester> getAllSemesters() {
        return semesterRepository.findAll();
    }

    // Get semester by semester code
    public Semester getSemesterBySemesterCode(String semesterCode) {
        return semesterRepository.findBySemesterCode(semesterCode)
                .orElseThrow(() -> new IllegalArgumentException("Semester not found"));
    }

    // Get current semester
    public Semester getCurrentSemester() {
        String currentSemesterCode = generateSemesterCodeBaseOnRealTime();
        Optional<Semester> currentSemester = semesterRepository.findBySemesterCode(currentSemesterCode);
        if (!currentSemester.isPresent()) {
            createSemester(currentSemesterCode);
        }
        return getSemesterBySemesterCode(currentSemesterCode);
    }

    // Get Recommended Semester
    public List<String> getRecomentListSemester() {
        List<String> result = new ArrayList<>();

        String currentSemesterCode = generateSemesterCodeBaseOnRealTime();
        Optional<Semester> currentSemester = semesterRepository.findBySemesterCode(currentSemesterCode);
        if (!currentSemester.isPresent()) {
            createSemester(currentSemesterCode);
        }
        result.add(currentSemesterCode);

        String nextSemesterCode = findNextSemesterCode();
        Optional<Semester> nextSemester = semesterRepository.findBySemesterCode(nextSemesterCode);
        if (!nextSemester.isPresent()) {
            createSemester(nextSemesterCode);
        }
        result.add(nextSemesterCode);

        return result;
    }

    ////////////// Service for post method - create data //////////////
    // Create a new semester with all fields
    public Semester createSemester(CreateSemesterRequest request) {
        Optional<Semester> semester = semesterRepository.findBySemesterCode(request.getSemesterCode());
        if (semester.isPresent()) {
            return semester.get();
        }

        if (request.getSemesterName() == null || request.getSemesterName().isEmpty()) {
            throw new IllegalArgumentException("Semester name is required");
        }

        if (request.getSemesterDuration() == null
                || request.getSemesterDuration().isEmpty()) {
            throw new IllegalArgumentException("Semester duration is required");
        }

        if (isSemesterCodeValid(request.getSemesterCode()) == false) {
            throw new IllegalArgumentException("Semester code is invalid");
        }

        Semester newSemester = new Semester();
        newSemester.setSemesterCode(request.getSemesterCode());
        newSemester.setSemesterName(request.getSemesterName());
        newSemester.setSemesterDuration(request.getSemesterDuration());

        return semesterRepository.save(newSemester);
    }

    // Create a new semester with semester code
    public Semester createSemester(String semesterCode) {
        Optional<Semester> semester = semesterRepository.findBySemesterCode(semesterCode);
        if (semester.isPresent()) {
            return semester.get();
        }

        if (semesterCode.substring(0, 2).equals("HK") == false) {
            throw new IllegalArgumentException("Semester code must start with 'HK'");
        }

        if (semesterCode.length() != 5) {
            throw new IllegalArgumentException("Semester code must have 5 characters, start with 'HK' and 3 digits");
        }

        for (int i = 2; i < 5; i++) {
            if (Character.isDigit(semesterCode.charAt(i)) == false) {
                throw new IllegalArgumentException(
                        "Semester code must have 5 characters, start with 'HK' and 3 digits");
            }
        }

        Semester newSemester = new Semester(semesterCode);
        return semesterRepository.save(newSemester);
    }

    // Create Semester Base on Recommend
    public List<Semester> createSemesterBaseOnRecommend() {
        List<String> recommendList = getRecomentListSemester();
        List<Semester> result = new ArrayList<>();

        for (String semesterCode : recommendList) {
            result.add(getSemesterBySemesterCode(semesterCode));
        }

        return result;
    }

    ////////////// Service for put method - update data //////////////

    ////////////// Service for delete method - delete data //////////////
}
