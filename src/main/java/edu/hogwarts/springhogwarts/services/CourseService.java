package edu.hogwarts.springhogwarts.services;

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

    public CourseService(CourseRepository courseRepository, TeacherRepository teacherRepository, StudentRepository studentRepository) {
        this.courseRepository = courseRepository;
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
    }


    public Optional<Course> getSingleCourse(long id) {
        return courseRepository.findById(id);
    }

    public List<Course> getCourses() {
        return courseRepository.findAll();
    }

    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }

    @Transactional
    public Course updateCourse(Course course, long id) {
        Course courseInDb = courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Course not found"));

        courseInDb.setCurrent(course.isCurrent());
        courseInDb.setSchoolyear(course.getSchoolyear());
        courseInDb.setSubject(course.getSubject());

        return courseInDb;
    }

    public void deleteCourse(long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Could not find course"));

        for (Student student :
                course.getStudents()) {
            student.removeCourse(course);
        }

        course.getTeacher().removeCourse(course);
        courseRepository.delete(course);
    }

    public Course removeTeacherFromCourse(long courseId, long teacherId) {
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new EntityNotFoundException("Teacher not found"));


        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course not found"));

        course.removeTeacher(teacher);

        //Husk at gemme dine ændringer!
        return courseRepository.save(course);
    }

    public Course removeStudentFromCourse(long courseId, long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course not found"));

        course.removeStudent(student);

        return courseRepository.save(course);
    }

    public Course assignTeacherToCourse(long courseId, long teacherId) {
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new EntityNotFoundException("Teacher not found"));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course not found"));

        course.setTeacher(teacher);

        return courseRepository.save(course);
    }

    public Course enrollStudentToCourse(long courseId, long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course not found"));

        course.assignStudent(student);

        return courseRepository.save(course);
    }

    public Teacher getTeacherAssignedToCourse(long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course not found"));

        return course.getTeacher();
    }

    public List<Student> getStudentsEnrolledInCourse(long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course not found"));

        return course.getStudents().stream().toList();
    }
}
