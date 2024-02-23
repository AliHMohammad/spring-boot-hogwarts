package edu.hogwarts.springhogwarts.services;

import edu.hogwarts.springhogwarts.dto.course.RequestCourseDTO;
import edu.hogwarts.springhogwarts.dto.course.RequestCourseDTOMapper;
import edu.hogwarts.springhogwarts.dto.course.ResponseCourseDTO;
import edu.hogwarts.springhogwarts.dto.course.ResponseCourseDTOMapper;
import edu.hogwarts.springhogwarts.dto.student.ResponseStudentDTO;
import edu.hogwarts.springhogwarts.dto.student.ResponseStudentDTOMapper;
import edu.hogwarts.springhogwarts.dto.student.request.StudentDTOIdsList;
import edu.hogwarts.springhogwarts.dto.student.request.StudentDTONamesList;
import edu.hogwarts.springhogwarts.dto.teacher.ResponseTeacherDTO;
import edu.hogwarts.springhogwarts.dto.teacher.ResponseTeacherDTOMapper;
import edu.hogwarts.springhogwarts.dto.teacher.request.TeacherDTOId;
import edu.hogwarts.springhogwarts.models.Course;
import edu.hogwarts.springhogwarts.models.Student;
import edu.hogwarts.springhogwarts.models.Teacher;
import edu.hogwarts.springhogwarts.repositories.CourseRepository;
import edu.hogwarts.springhogwarts.repositories.StudentRepository;
import edu.hogwarts.springhogwarts.repositories.TeacherRepository;
import jakarta.persistence.EntityNotFoundException;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final ResponseCourseDTOMapper responseCourseDTOMapper;
    private final ResponseTeacherDTOMapper responseTeacherDTOMapper;
    private final ResponseStudentDTOMapper responseStudentDTOMapper;
    private final RequestCourseDTOMapper requestCourseDTOMapper;

    public CourseService(CourseRepository courseRepository, TeacherRepository teacherRepository, StudentRepository studentRepository, ResponseCourseDTOMapper responseCourseDTOMapper, ResponseStudentDTOMapper responseStudentDTOMapper, ResponseTeacherDTOMapper responseTeacherDTOMapper,
                         RequestCourseDTOMapper requestCourseDTOMapper) {
        this.courseRepository = courseRepository;
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
        this.responseCourseDTOMapper = responseCourseDTOMapper;
        this.responseTeacherDTOMapper = responseTeacherDTOMapper;
        this.responseStudentDTOMapper = responseStudentDTOMapper;
        this.requestCourseDTOMapper = requestCourseDTOMapper;
    }


    public ResponseCourseDTO getSingleCourse(long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Course not found by id"));

        return responseCourseDTOMapper.apply(course);
    }

    public List<ResponseCourseDTO> getCourses() {
        return courseRepository.findAll()
                .stream()
                .map(responseCourseDTOMapper)
                .toList();
    }

    public ResponseCourseDTO createCourse(RequestCourseDTO requestCourseDTO) {
        Course c = requestCourseDTOMapper.apply(requestCourseDTO);
        courseRepository.save(c);
        return responseCourseDTOMapper.apply(c);
    }

    @Transactional
    public ResponseCourseDTO updateCourse(RequestCourseDTO requestCourseDTO, long id) {
        courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Course not found"));
        Course course = requestCourseDTOMapper.apply(requestCourseDTO);

        course.setId(id);

        courseRepository.save(course);
        return responseCourseDTOMapper.apply(course);
    }

    public ResponseCourseDTO deleteCourse(long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Could not find course"));
        ResponseCourseDTO responseCourseDTO = responseCourseDTOMapper.apply(course);

        for (Student student :
                course.getStudents()) {
            student.removeCourse(course);
        }


        course.setTeacher(null);
        courseRepository.delete(course);
        return responseCourseDTO;
    }

    public ResponseCourseDTO removeStudentFromCourse(long courseId, long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course not found"));

        course.removeStudent(student);

        courseRepository.save(course);
        return responseCourseDTOMapper.apply(course);
    }

    public ResponseTeacherDTO getTeacherAssignedToCourse(long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course not found"));

        return course.getTeacher() == null ? null : responseTeacherDTOMapper.apply(course.getTeacher());
    }

    public List<ResponseStudentDTO> getStudentsEnrolledInCourse(long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course not found"));

        return course.getStudents()
                .stream()
                .map(responseStudentDTOMapper)
                .toList();
    }

    public ResponseCourseDTO updateTeacherInCourse(long courseId, TeacherDTOId teacherDTOId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course not found"));

        //Hvis teacherDTOId er null, så fjern teacher fra course
        if (teacherDTOId.id() == null) {
            course.setTeacher(null);
        } else {
            Teacher teacherInDb = teacherRepository.findById(teacherDTOId.id())
                    .orElseThrow(() -> new EntityNotFoundException("Teacher not found"));

            course.setTeacher(teacherInDb);
        }

        courseRepository.save(course);
        return responseCourseDTOMapper.apply(course);
    }

    @Transactional
    public ResponseCourseDTO AssignStudentsToCourse(long courseId, StudentDTOIdsList studentDTOIdsList) throws BadRequestException {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course not found"));

        for (Long studentId : studentDTOIdsList.students()) {
            Student student = studentRepository.findById(studentId)
                    .orElseThrow(() -> new EntityNotFoundException("Student with id " + studentId + " not found"));

            if (student.getSchoolYear() != course.getSchoolyear()) {
                throw new BadRequestException("Can not assign student with id " + student.getId() + " with course because student schoolyear differs from course schoolYear");
            }

            course.assignStudent(student);
        }

        courseRepository.save(course);
        return responseCourseDTOMapper.apply(course);
    }

    public ResponseCourseDTO AssignStudentsToCourseWithNames(long courseId, StudentDTONamesList studentDTONamesList) throws BadRequestException {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course not found"));

        for (String fullName : studentDTONamesList.students()) {
            Student s = new Student(fullName);
            Student studentInDb = studentRepository.findFirstByFirstNameContainingOrLastNameContaining(s.getFirstName(), s.getLastName())
                    .orElseThrow(() -> new EntityNotFoundException("Student with name containing" + fullName + " not found"));

            if (studentInDb.getSchoolYear() != course.getSchoolyear()) {
                throw new BadRequestException("Can not assign student with id " + studentInDb.getId() + " with course because student schoolyear differs from course schoolYear");
            }

            course.assignStudent(studentInDb);
        }

        courseRepository.save(course);
        return responseCourseDTOMapper.apply(course);
    }
}