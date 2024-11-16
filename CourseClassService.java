package com.hcmut.gradeportal.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.hcmut.gradeportal.dtos.courseClass.request.ChangeTeacherRequest;
import com.hcmut.gradeportal.dtos.courseClass.request.CreateCourseClassRequest;
import com.hcmut.gradeportal.dtos.courseClass.request.GetCourseClassRequest;
import com.hcmut.gradeportal.dtos.courseClass.request.UpdateClassStatusRequest;
import com.hcmut.gradeportal.dtos.courseClass.request.UpdateStudentsInClassRequest;
import com.hcmut.gradeportal.entities.Course;
import com.hcmut.gradeportal.entities.CourseClass;
import com.hcmut.gradeportal.entities.SheetMark;
import com.hcmut.gradeportal.entities.Student;
import com.hcmut.gradeportal.entities.Teacher;
import com.hcmut.gradeportal.entities.enums.ClassStatus;
import com.hcmut.gradeportal.repositories.CourseClassRepository;
import com.hcmut.gradeportal.repositories.CourseRepository;
import com.hcmut.gradeportal.repositories.SheetMarkRepository;
import com.hcmut.gradeportal.repositories.StudentRepository;
import com.hcmut.gradeportal.repositories.TeacherRepository;
import com.hcmut.gradeportal.specification.CourseClassSpecification;

@Service
public class CourseClassService {
        private final CourseRepository courseRepository;
        private final CourseClassRepository courseClassRepository;

        private final StudentRepository studentRepository;
        private final TeacherRepository teacherRepository;

        private final SheetMarkRepository sheetMarkRepository;

        public CourseClassService(CourseRepository courseRepository, CourseClassRepository courseClassRepository,
                        StudentRepository studentRepository,
                        TeacherRepository teacherRepository,
                        SheetMarkRepository sheetMarkRepository) {
                this.courseRepository = courseRepository;
                this.courseClassRepository = courseClassRepository;

                this.studentRepository = studentRepository;
                this.teacherRepository = teacherRepository;

                this.sheetMarkRepository = sheetMarkRepository;
        }

        ////////////// Service for get method - read data //////////////

        // Lấy ra tất cả các CourseClass trong hệ thống
        public List<CourseClass> getAllCourseClasses() {
                return courseClassRepository.findAll();
        }

        // Lấy ra các CourseClass theo điều kiện
        public List<CourseClass> getCourseClassesBySpecification(GetCourseClassRequest request) {
                Student student = null;
                if (request.getStudentId() != null && !request.getStudentId().isEmpty()) {
                        student = studentRepository.findById(request.getStudentId())
                                        .orElseThrow(() -> new IllegalArgumentException("Student not found"));
                }
                // Sử dụng Specifications để lọc dữ liệu
                Specification<CourseClass> spec = Specification
                                .where(CourseClassSpecification.hasCourseCode(request.getCourseCode()))
                                .and(CourseClassSpecification.hasSemesterCode(request.getSemesterCode()))
                                .and(CourseClassSpecification.hasClassName(request.getClassName()))
                                .and(CourseClassSpecification.hasTeacherId(request.getTeacherId()))
                                .and(CourseClassSpecification.hasClassStatus(request.getClassStatus()))
                                .and(CourseClassSpecification.hasStudent(student));

                // Truy vấn dữ liệu từ database
                return courseClassRepository.findAll(spec);
        }

        ////////////// Service for post method - create data //////////////

        // Hàm này dùng khi init dữ liệu, lúc này teacherId sẽ là teacherId của Teacher
        // chứ không phải là ID của Teacher-User
        public CourseClass createCourseClassWhenInit(CreateCourseClassRequest createCourseClassRequest) {
                Optional<CourseClass> optCourseClass = courseClassRepository
                                .findByCourseCodeAndSemesterCodeAndClassName(
                                                createCourseClassRequest.getCourseCode(),
                                                createCourseClassRequest.getSemesterCode(),
                                                createCourseClassRequest.getClassName());
                if (optCourseClass.isPresent()) {
                        throw new IllegalArgumentException("Course class already exists");
                }

                CourseClass newClass = new CourseClass();

                newClass.setCourseCode(createCourseClassRequest.getCourseCode());
                newClass.setSemesterCode(createCourseClassRequest.getSemesterCode());
                newClass.setClassName(createCourseClassRequest.getClassName());
                if (createCourseClassRequest.getClassStatus() == null) {
                        newClass.setClassStatus(ClassStatus.inProgress);
                } else {
                        newClass.setClassStatus(createCourseClassRequest.getClassStatus());
                }

                if (createCourseClassRequest.getTeacherId() != null
                                && !createCourseClassRequest.getTeacherId().isEmpty()) {
                        Teacher teacher = teacherRepository.findByTeacherId(createCourseClassRequest.getTeacherId())
                                        .orElseThrow(() -> new IllegalArgumentException("Teacher not found"));

                        newClass.setTeacher(teacher);
                }

                Course course = courseRepository.findByCourseCode(createCourseClassRequest.getCourseCode())
                                .orElseThrow(() -> new IllegalArgumentException("Course not found"));

                newClass.setCourse(course);

                List<Student> studentsOfClass = new ArrayList<>();
                newClass.setListOfStudents(studentsOfClass);

                courseClassRepository.save(newClass);

                if (createCourseClassRequest.getStudentIds() != null) {
                        for (String studentId : createCourseClassRequest.getStudentIds()) {
                                Student student = studentRepository.findById(studentId)
                                                .orElseThrow(() -> new IllegalArgumentException(
                                                                "Student not found with ID: " + studentId));

                                // Kiểm tra xem lớp học đã có trong listOfCourseClasses của sinh viên hay chưa
                                Boolean alreadyEnrolled = student.getListOfCourseClasses().stream()
                                                .anyMatch(courseClass -> courseClass.getCourseCode()
                                                                .equals(newClass.getCourseCode()) &&
                                                                courseClass.getSemesterCode()
                                                                                .equals(newClass.getSemesterCode())
                                                                &&
                                                                courseClass.getClassName()
                                                                                .equals(newClass.getClassName()));

                                // Nếu lớp học chưa có trong danh sách, thêm vào
                                if (!alreadyEnrolled) {
                                        student.getListOfCourseClasses().add(newClass);
                                        studentRepository.save(student); // Lưu lại sinh viên sau khi cập nhật
                                }

                                studentsOfClass.add(student);
                        }
                }

                newClass.setListOfStudents(studentsOfClass);

                courseClassRepository.save(newClass);

                if (newClass.getTeacher() != null && newClass.getTeacher().getId() != null) {
                        for (Student student : studentsOfClass) {
                                Teacher teacher = teacherRepository.findById(createCourseClassRequest.getTeacherId())
                                                .orElseThrow(() -> new IllegalArgumentException("Teacher not found"));

                                Optional<SheetMark> sheetMark = sheetMarkRepository
                                                .findByStudentIdAndCourseCodeAndSemesterCodeAndClassName(
                                                                student.getId(), newClass.getCourseCode(),
                                                                newClass.getSemesterCode(), newClass.getClassName());
                                if (!sheetMark.isPresent()) {
                                        SheetMark newSheetMark = new SheetMark(student, teacher,
                                                        newClass);
                                        sheetMarkRepository.save(newSheetMark);
                                }
                        }
                }

                return newClass;
        }

        // Hàm này dùng khi tạo mới CourseClass, lúc này teacherId sẽ là ID của
        // Teacher-User
        // Hàm này sẽ được controller gọi
        public CourseClass createCourseClass(CreateCourseClassRequest createCourseClassRequest) {
                Optional<CourseClass> optCourseClass = courseClassRepository
                                .findByCourseCodeAndSemesterCodeAndClassName(
                                                createCourseClassRequest.getCourseCode(),
                                                createCourseClassRequest.getSemesterCode(),
                                                createCourseClassRequest.getClassName());
                if (optCourseClass.isPresent()) {
                        throw new IllegalArgumentException("Course class already exists");
                }

                CourseClass newClass = new CourseClass();

                newClass.setCourseCode(createCourseClassRequest.getCourseCode());
                newClass.setSemesterCode(createCourseClassRequest.getSemesterCode());
                newClass.setClassName(createCourseClassRequest.getClassName());

                // Tìm kiếm giáo viên bằng teacherId (giả sử teacherId là ID của giáo viên)
                if (createCourseClassRequest.getTeacherId() != null
                                && !createCourseClassRequest.getTeacherId().isEmpty()) {
                        Teacher teacher = teacherRepository.findById(createCourseClassRequest.getTeacherId())
                                        .orElseThrow(() -> new IllegalArgumentException("Teacher not found"));

                        newClass.setTeacher(teacher);
                }

                Course course = courseRepository.findByCourseCode(createCourseClassRequest.getCourseCode())
                                .orElseThrow(() -> new IllegalArgumentException("Course not found"));

                newClass.setCourse(course);

                List<Student> studentsOfClass = new ArrayList<>();
                newClass.setListOfStudents(studentsOfClass);
                if (createCourseClassRequest.getClassStatus() == null) {
                        newClass.setClassStatus(ClassStatus.inProgress);
                } else {
                        newClass.setClassStatus(createCourseClassRequest.getClassStatus());
                }

                courseClassRepository.save(newClass);

                if (createCourseClassRequest.getStudentIds() != null) {
                        for (String studentId : createCourseClassRequest.getStudentIds()) {
                                Student student = studentRepository.findById(studentId)
                                                .orElseThrow(() -> new IllegalArgumentException(
                                                                "Student not found with ID: " + studentId));

                                // Kiểm tra xem lớp học đã có trong listOfCourseClasses của sinh viên hay chưa
                                Boolean alreadyEnrolled = student.getListOfCourseClasses().stream()
                                                .anyMatch(courseClass -> courseClass.getCourseCode()
                                                                .equals(newClass.getCourseCode()) &&
                                                                courseClass.getSemesterCode()
                                                                                .equals(newClass.getSemesterCode())
                                                                &&
                                                                courseClass.getClassName()
                                                                                .equals(newClass.getClassName()));

                                // Nếu lớp học chưa có trong danh sách, thêm vào
                                if (!alreadyEnrolled) {
                                        student.getListOfCourseClasses().add(newClass);
                                        studentRepository.save(student); // Lưu lại sinh viên sau khi cập nhật
                                }

                                studentsOfClass.add(student);

                        }
                }

                newClass.setListOfStudents(studentsOfClass);
                courseClassRepository.save(newClass);

                if (createCourseClassRequest.getTeacherId() != null
                                && !createCourseClassRequest.getTeacherId().isEmpty()) {
                        Teacher teacher = teacherRepository.findById(createCourseClassRequest.getTeacherId())
                                        .orElseThrow(() -> new IllegalArgumentException("Teacher not found"));
                        for (Student student : studentsOfClass) {
                                Optional<SheetMark> sheetMark = sheetMarkRepository
                                                .findByStudentIdAndCourseCodeAndSemesterCodeAndClassName(
                                                                student.getId(), newClass.getCourseCode(),
                                                                newClass.getSemesterCode(), newClass.getClassName());
                                if (!sheetMark.isPresent()) {
                                        SheetMark newSheetMark = new SheetMark(student, teacher,
                                                        newClass);
                                        sheetMarkRepository.save(newSheetMark);
                                }
                        }
                }

                return newClass;
        }

        ////////////// Service for put method - update data //////////////

        // Thay đổi giáo viên của lớp học
        public CourseClass changeTeacherOfCourseClass(ChangeTeacherRequest request) {
                CourseClass courseClass = courseClassRepository.findByCourseCodeAndSemesterCodeAndClassName(
                                request.getCourseCode(), request.getSemesterCode(), request.getClassName())
                                .orElseThrow(() -> new IllegalArgumentException("Course class not found"));

                if (courseClass.getTeacher() == null || courseClass.getTeacher().getTeacherId() == "") {
                        Teacher teacher = teacherRepository.findById(request.getTeacherId())
                                        .orElseThrow(() -> new IllegalArgumentException("Teacher not found"));

                        courseClass.setTeacher(teacher);
                        courseClassRepository.save(courseClass);

                        for (Student student : courseClass.getListOfStudents()) {
                                Optional<SheetMark> sheetMark = sheetMarkRepository
                                                .findByStudentIdAndCourseCodeAndSemesterCodeAndClassName(
                                                                student.getId(), courseClass.getCourseCode(),
                                                                courseClass.getSemesterCode(),
                                                                courseClass.getClassName());
                                if (!sheetMark.isPresent()) {
                                        SheetMark newSheetMark = new SheetMark(student, teacher,
                                                        courseClass);
                                        sheetMarkRepository.save(newSheetMark);
                                } else {
                                        sheetMark.get().setTeacher(teacher);
                                        sheetMarkRepository.save(sheetMark.get());
                                }
                        }
                } else {
                        Teacher teacher = teacherRepository.findById(request.getTeacherId())
                                        .orElseThrow(() -> new IllegalArgumentException("Teacher not found"));

                        courseClass.setTeacher(teacher);
                        courseClassRepository.save(courseClass);

                        for (Student student : courseClass.getListOfStudents()) {
                                Optional<SheetMark> sheetMark = sheetMarkRepository
                                                .findByStudentIdAndCourseCodeAndSemesterCodeAndClassName(
                                                                student.getId(), courseClass.getCourseCode(),
                                                                courseClass.getSemesterCode(),
                                                                courseClass.getClassName());
                                if (sheetMark.isPresent()) {
                                        sheetMark.get().setTeacher(teacher);
                                        sheetMarkRepository.save(sheetMark.get());
                                } else {
                                        SheetMark newSheetMark = new SheetMark(student, teacher,
                                                        courseClass);
                                        sheetMarkRepository.save(newSheetMark);
                                }
                        }
                }

                return courseClass;
        }

        // Cập nhập danh sách sinh viên trong lớp học
        public CourseClass updateStudentsOfCourseClass(UpdateStudentsInClassRequest request) {
                CourseClass courseClass = courseClassRepository.findByCourseCodeAndSemesterCodeAndClassName(
                                request.getCourseCode(), request.getSemesterCode(), request.getClassName())
                                .orElseThrow(() -> new IllegalArgumentException("Course class not found"));

                List<Student> oldStudents = courseClass.getListOfStudents();
                List<String> newStudentIds = request.getNewStudentIds();

                List<Student> newStudents = new ArrayList<>();
                for (String studentId : newStudentIds) {
                        Student student = studentRepository.findById(studentId)
                                        .orElseThrow(() -> new IllegalArgumentException(
                                                        "Student not found with ID: " + studentId));
                        newStudents.add(student);
                }

                for (Student student : oldStudents) {
                        String studentId = student.getId();
                        if (!newStudentIds.contains(studentId)) {
                                sheetMarkRepository.deleteByStudentIdAndCourseCodeAndSemesterCodeAndClassName(
                                                studentId, courseClass.getCourseCode(),
                                                courseClass.getSemesterCode(),
                                                courseClass.getClassName());
                                student.getListOfCourseClasses().remove(courseClass);
                                studentRepository.save(student);
                        }
                }

                courseClass.setListOfStudents(newStudents);
                courseClassRepository.save(courseClass);

                if (courseClass.getTeacher() != null) {
                        for (Student student : newStudents) {
                                String studentId = student.getId();

                                Optional<SheetMark> sheetMark = sheetMarkRepository
                                                .findByStudentIdAndCourseCodeAndSemesterCodeAndClassName(
                                                                studentId, courseClass.getCourseCode(),
                                                                courseClass.getSemesterCode(),
                                                                courseClass.getClassName());
                                if (!sheetMark.isPresent()) {
                                        SheetMark newSheetMark = new SheetMark(student,
                                                        courseClass.getTeacher(), courseClass);
                                        student.getListOfCourseClasses().add(courseClass);
                                        studentRepository.save(student);
                                        sheetMarkRepository.save(newSheetMark);
                                }
                        }
                }

                return courseClass;
        }

        // Cập nhập trạng thái của lớp học

        // Thay đổi course code của lớp học

        //////////// Service for delete method - delete data //////////////
        public CourseClass deleteCourseClass(UpdateClassStatusRequest request){
                CourseClass deleteClass = courseClassRepository.findByCourseCodeAndSemesterCodeAndClassName(
                                request.getCourseCode(), request.getSemesterCode(), request.getClassName())
                                .orElseThrow(() -> new IllegalArgumentException("Course class not found"));
                for(int i=0; i<deleteClass.getListOfStudents().size();i++){
                        Student temp=deleteClass.getListOfStudents().get(i);
                        sheetMarkRepository.deleteByStudentIdAndCourseCodeAndSemesterCodeAndClassName(
                                temp.getId(), deleteClass.getCourseCode(), deleteClass.getSemesterCode(), deleteClass.getClassName());
                        // for(int j=0;j<temp.getListOfCourseClasses().size();j++){
                        //         if(temp.getListOfCourseClasses().get(j)==deleteClass){
                        //                 temp.getListOfCourseClasses().remove();
                        //                 studentRepository.save(temp);
                        //                 break;
                        //         }
                        // }
                        List<CourseClass> templist=temp.getListOfCourseClasses();
                        templist.remove(deleteClass);
                        temp.setListOfCourseClasses(templist);
                        studentRepository.save(temp);
                }
                deleteClass.setListOfStudents(new ArrayList<Student>());
                courseClassRepository.save(deleteClass);
               courseClassRepository.deleteByCourseCodeAndSemesterCodeAndClassName(
                deleteClass.getCourseCode(),deleteClass.getSemesterCode(),deleteClass.getClassName());
                return deleteClass;
        }
        public CourseClass getclass(String cou, String se,String nam){
                return courseClassRepository.findByCourseCodeAndSemesterCodeAndClassNameAndClassStatus(cou,se,nam,ClassStatus.inProgress);}
        public CourseClass updateStatusCourseClass(UpdateClassStatusRequest request){
                //CourseClass updateClass=courseClassRepository.findByCourseCodeAndSemesterCodeAndClassNameAndClassStatus(cou,se,nam,ClassStatus.inProgress);
                //CourseClass updateClass=courseClassRepository.findByCourseCodeAndSemesterCodeAndClassName(cou,se,nam).get();
                CourseClass updateClass = courseClassRepository.findByCourseCodeAndSemesterCodeAndClassName(
                                request.getCourseCode(), request.getSemesterCode(), request.getClassName())
                                .orElseThrow(() -> new IllegalArgumentException("Course class not found"));
                if(updateClass.getClassStatus()==ClassStatus.Completed)
                        return updateClass;
                List<Student> liststudent=updateClass.getListOfStudents();
                boolean iscompleted=true;
                for(Student student:liststudent)
                {
                        if(sheetMarkRepository.findByStudentIdAndCourseCodeAndSemesterCodeAndClassName(student.getId(), 
                        updateClass.getCourseCode(), updateClass.getSemesterCode(), updateClass.getClassName()).get().getCK().size()==0)
                        {
                                iscompleted=false;break;
                        }
                }
                if(iscompleted) {
                        updateClass.setClassStatus(ClassStatus.Completed);
                        courseClassRepository.save(updateClass);
                }
                return updateClass;
        }
        //có thể thêm request về del class
}
