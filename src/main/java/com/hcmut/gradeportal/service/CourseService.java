package com.hcmut.gradeportal.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.hcmut.gradeportal.dtos.course.request.CreateCourseRequest;
import com.hcmut.gradeportal.dtos.course.request.GetCourseRequest;
import com.hcmut.gradeportal.entities.Course;
import com.hcmut.gradeportal.entities.CourseClass;
import com.hcmut.gradeportal.entities.Student;
import com.hcmut.gradeportal.repositories.CourseClassRepository;
import com.hcmut.gradeportal.repositories.SheetMarkRepository;
import com.hcmut.gradeportal.repositories.StudentRepository;

import com.hcmut.gradeportal.repositories.CourseRepository;

@Service
public class CourseService {
    private final CourseRepository courseRepository;
    private final CourseClassRepository courseClassRepository;
    private final SheetMarkRepository sheetMarkRepository;
    private final StudentRepository studentRepository;


    public CourseService(CourseRepository courseRepository, CourseClassRepository courseClassRepository, 
                            SheetMarkRepository sheetMarkRepository, StudentRepository studentRepository) {
        this.courseRepository = courseRepository;
        this.courseClassRepository = courseClassRepository;
        this.sheetMarkRepository = sheetMarkRepository;
        this.studentRepository = studentRepository;
    }

    ////////////// Service for get method - read data //////////////
    // Lấy ra tất cả các Course trong hệ thống
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    // Lấy ra Course theo courseCode
    public Course getCourseByCourseCode(String courseCode) {
        return courseRepository.findByCourseCode(courseCode)
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));
    }

    // Lấy ra Course theo số tín chỉ
    public List<Course> getCourseByCredit(int credit) {
        return courseRepository.findByCredit(credit);
    }

    ////////////// Service for post method - create data //////////////
    // Tạo một Course mới
    public Course createCourse(CreateCourseRequest createCourseRequest) {
        Optional<Course> courseOptional = courseRepository.findByCourseCode(createCourseRequest.getCourseCode());
        if (courseOptional.isPresent()) {
            throw new IllegalArgumentException("Course code already exists");
        }
        Course course = new Course();
        course.setCourseCode(createCourseRequest.getCourseCode());
        course.setCourseName(createCourseRequest.getCourseName());
        course.setCredit(createCourseRequest.getCredit());

        Integer totalCoefficient = 0;

        if (createCourseRequest.getCoefficient_of_BT() == null) {
            throw new IllegalArgumentException("Coefficient of BT is required");
        } else {
            totalCoefficient += createCourseRequest.getCoefficient_of_BT();
            course.setCoefficient_of_BT(createCourseRequest.getCoefficient_of_BT());
        }

        if (createCourseRequest.getCoefficient_of_BTL() == null) {
            throw new IllegalArgumentException("Coefficient of TH is required");
        } else {
            totalCoefficient += createCourseRequest.getCoefficient_of_BTL();
            course.setCoefficient_of_BTL(createCourseRequest.getCoefficient_of_BTL());
        }

        if (createCourseRequest.getCoefficient_of_TN() == null) {
            throw new IllegalArgumentException("Coefficient of TH is required");
        } else {
            totalCoefficient += createCourseRequest.getCoefficient_of_TN();
            course.setCoefficient_of_TN(createCourseRequest.getCoefficient_of_TN());
        }

        if (createCourseRequest.getCoefficient_of_GK() == null) {
            throw new IllegalArgumentException("Coefficient of GK is required");
        } else {
            totalCoefficient += createCourseRequest.getCoefficient_of_GK();
            course.setCoefficient_of_GK(createCourseRequest.getCoefficient_of_GK());
        }

        if (createCourseRequest.getCoefficient_of_CK() == null) {
            throw new IllegalArgumentException("Coefficient of CK is required");
        } else {
            totalCoefficient += createCourseRequest.getCoefficient_of_CK();
            course.setCoefficient_of_CK(createCourseRequest.getCoefficient_of_CK());
        }

        if (totalCoefficient != 100) {
            throw new IllegalArgumentException("Total coefficient must be 100");
        }

        return courseRepository.save(course);
    }

    ////////////// Service for put method - update data //////////////
    /// Sửa thông tin của một lớp học
    public Course updateCourse(CreateCourseRequest updateCourseRequest) {
        // Fetch existing course
        Course course = courseRepository.findByCourseCode(updateCourseRequest.getCourseCode())
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));

        // Update fields if not null
        if (updateCourseRequest.getCourseName() != null) {
            course.setCourseName(updateCourseRequest.getCourseName());
        }

        if (updateCourseRequest.getCredit() != null) {
            course.setCredit(updateCourseRequest.getCredit());
        }

        Integer totalCoefficient = 0;

        // Coefficients can be updated or retained if null
        if (updateCourseRequest.getCoefficient_of_BT() != null) {
            totalCoefficient += updateCourseRequest.getCoefficient_of_BT();
            course.setCoefficient_of_BT(updateCourseRequest.getCoefficient_of_BT());
        } else {
            totalCoefficient += course.getCoefficient_of_BT();
        }

        if (updateCourseRequest.getCoefficient_of_BTL() != null) {
            totalCoefficient += updateCourseRequest.getCoefficient_of_BTL();
            course.setCoefficient_of_BTL(updateCourseRequest.getCoefficient_of_BTL());
        } else {
            totalCoefficient += course.getCoefficient_of_BTL();
        }

        if (updateCourseRequest.getCoefficient_of_TN() != null) {
            totalCoefficient += updateCourseRequest.getCoefficient_of_TN();
            course.setCoefficient_of_TN(updateCourseRequest.getCoefficient_of_TN());
        } else {
            totalCoefficient += course.getCoefficient_of_TN();
        }

        if (updateCourseRequest.getCoefficient_of_GK() != null) {
            totalCoefficient += updateCourseRequest.getCoefficient_of_GK();
            course.setCoefficient_of_GK(updateCourseRequest.getCoefficient_of_GK());
        } else {
            totalCoefficient += course.getCoefficient_of_GK();
        }

        if (updateCourseRequest.getCoefficient_of_CK() != null) {
            totalCoefficient += updateCourseRequest.getCoefficient_of_CK();
            course.setCoefficient_of_CK(updateCourseRequest.getCoefficient_of_CK());
        } else {
            totalCoefficient += course.getCoefficient_of_CK();
        }

        // Ensure total coefficient is 100
        if (totalCoefficient != 100) {
            throw new IllegalArgumentException("Total coefficient must equal 100");
        }

        return courseRepository.save(course);
    }

    ////////////// Service for delete method - delete data //////////////
    // Xóa 1 khóa học
    public void deleteCourse(GetCourseRequest request) {
        // Verify if the course exists
        Course course = courseRepository.findByCourseCode(request.getCourseCode())
            .orElseThrow(() -> new IllegalArgumentException("Course not found"));

        // Fetch and delete all related CourseClass records
        List<CourseClass> courseClasses = courseClassRepository.findAll()
            .stream()
            .filter(cc -> cc.getCourseCode().equals(request.getCourseCode()))
            .collect(Collectors.toList());

        for (CourseClass courseClass : courseClasses) {
             // For each student in the course class, delete associated marks and remove class from student's list
            for (int i = 0; i < courseClass.getListOfStudents().size(); i++) {
                Student temp = courseClass.getListOfStudents().get(i);

                 // Delete sheet marks for the student in this course class
                sheetMarkRepository.deleteByStudentIdAndCourseCodeAndSemesterCodeAndClassName(
                    temp.getId(), courseClass.getCourseCode(), courseClass.getSemesterCode(), courseClass.getClassName());

                 // Remove the course class from the student's list of courses
                List<CourseClass> tempList = new ArrayList<>(temp.getListOfCourseClasses());
                tempList.remove(courseClass); // Remove the current course class
                temp.setListOfCourseClasses(tempList);

                // Save the updated student
                studentRepository.save(temp);
            }

            // Clear the students list in the course class
            courseClass.setListOfStudents(new ArrayList<Student>());

            // Save the updated course class
            courseClassRepository.save(courseClass);

            // Delete the course class from the repository
            courseClassRepository.delete(courseClass);
        }

        // Finally, delete the course itself
        courseRepository.delete(course);
    }

}