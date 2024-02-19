package edu.hogwarts.springhogwarts.services;

import edu.hogwarts.springhogwarts.dto.course.CourseDTO;
import edu.hogwarts.springhogwarts.dto.course.CourseDTOMapper;
import edu.hogwarts.springhogwarts.dto.student.StudentDTO;
import edu.hogwarts.springhogwarts.dto.student.StudentDTOMapper;
import edu.hogwarts.springhogwarts.dto.student.request.StudentDTOIdsList;
import edu.hogwarts.springhogwarts.dto.student.request.StudentDTONamesList;
import edu.hogwarts.springhogwarts.dto.teacher.TeacherDTO;
import edu.hogwarts.springhogwarts.dto.teacher.TeacherDTOMapper;
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

import java.util.List;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final CourseDTOMapper courseDTOMapper;
    private final TeacherDTOMapper teacherDTOMapper;
    private final StudentDTOMapper studentDTOMapper;

    public CourseService(CourseRepository courseRepository, TeacherRepository teacherRepository, StudentRepository studentRepository, CourseDTOMapper courseDTOMapper, StudentDTOMapper studentDTOMapper, TeacherDTOMapper teacherDTOMapper) {
        this.courseRepository = courseRepository;
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
        this.courseDTOMapper = courseDTOMapper;
        this.teacherDTOMapper = teacherDTOMapper;
        this.studentDTOMapper =studentDTOMapper;
    }


    public CourseDTO getSingleCourse(long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Course not found by id"));

        return courseDTOMapper.apply(course);
    }

    public List<CourseDTO> getCourses() {
        return courseRepository.findAll()
                .stream()
                .map(courseDTOMapper)
                .toList();
    }

    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }

    @Transactional
    public CourseDTO updateCourse(Course course, long id) {
        Course courseInDb = courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Course not found"));


        courseInDb.setCurrent(course.isCurrent());
        courseInDb.setSchoolyear(course.getSchoolyear());
        courseInDb.setSubject(course.getSubject());

        return courseDTOMapper.apply(courseInDb);
    }

    public CourseDTO deleteCourse(long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Could not find course"));
        CourseDTO courseDTO = courseDTOMapper.apply(course);

        for (Student student :
                course.getStudents()) {
            student.removeCourse(course);
        }

        /*course.getTeacher().removeCourse(course);*/
        course.setTeacher(null);
        courseRepository.delete(course);
        return courseDTO;
    }

    public CourseDTO removeTeacherFromCourse(long courseId, long teacherId) {
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new EntityNotFoundException("Teacher not found"));


        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course not found"));

        course.removeTeacher(teacher);

        //Husk at gemme dine ændringer!
        courseRepository.save(course);
        return courseDTOMapper.apply(course);
    }

    public CourseDTO removeStudentFromCourse(long courseId, long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course not found"));

        course.removeStudent(student);

        courseRepository.save(course);
        return courseDTOMapper.apply(course);
    }

    public CourseDTO assignTeacherToCourse(long courseId, long teacherId) {
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new EntityNotFoundException("Teacher not found"));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course not found"));

        course.setTeacher(teacher);

        courseRepository.save(course);
        return courseDTOMapper.apply(course);
    }

    public TeacherDTO getTeacherAssignedToCourse(long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course not found"));

        return teacherDTOMapper.apply(course.getTeacher());
    }

    public List<StudentDTO> getStudentsEnrolledInCourse(long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course not found"));

        return course.getStudents()
                .stream()
                .map(studentDTOMapper)
                .toList();
    }

    public CourseDTO updateTeacherInCourse(long courseId, TeacherDTOId teacherDTOId) {
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
        return courseDTOMapper.apply(course);
    }

    @Transactional
    public CourseDTO AssignStudentsToCourse(long courseId, StudentDTOIdsList studentDTOIdsList) throws BadRequestException {
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
        return courseDTOMapper.apply(course);
    }

    public CourseDTO AssignStudentsToCourseWithNames(long courseId, StudentDTONamesList studentDTONamesList) throws BadRequestException {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course not found"));

        for (String fullName : studentDTONamesList.students()) {
            Student s = new Student(fullName);
            Student studentInDb = studentRepository.findStudentByFirstNameContainingOrLastNameContaining(s.getFirstName(), s.getLastName())
                    .orElseThrow(() -> new EntityNotFoundException("Student with name containing" + fullName + " not found"));

            if (studentInDb.getSchoolYear() != course.getSchoolyear()) {
                throw new BadRequestException("Can not assign student with id " + studentInDb.getId() + " with course because student schoolyear differs from course schoolYear");
            }

            course.assignStudent(studentInDb);
        }

        courseRepository.save(course);
        return courseDTOMapper.apply(course);
    }
}