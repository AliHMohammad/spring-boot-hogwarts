package edu.hogwarts.springhogwarts.services;

import edu.hogwarts.springhogwarts.dto.course.CourseDTO;
import edu.hogwarts.springhogwarts.dto.course.CourseDTOMapper;
import edu.hogwarts.springhogwarts.dto.student.StudentDTO;
import edu.hogwarts.springhogwarts.dto.student.StudentDTOMapper;
import edu.hogwarts.springhogwarts.dto.teacher.TeacherDTO;
import edu.hogwarts.springhogwarts.dto.teacher.TeacherDTOMapper;
import edu.hogwarts.springhogwarts.models.Course;
import edu.hogwarts.springhogwarts.models.Student;
import edu.hogwarts.springhogwarts.models.Teacher;
import edu.hogwarts.springhogwarts.repositories.CourseRepository;
import edu.hogwarts.springhogwarts.repositories.StudentRepository;
import edu.hogwarts.springhogwarts.repositories.TeacherRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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

    public CourseDTO enrollStudentToCourse(long courseId, long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course not found"));

        course.assignStudent(student);

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
}
