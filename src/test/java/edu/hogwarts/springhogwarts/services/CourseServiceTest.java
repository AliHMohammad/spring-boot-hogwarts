package edu.hogwarts.springhogwarts.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import edu.hogwarts.springhogwarts.dto.course.CourseDTO;
import edu.hogwarts.springhogwarts.dto.course.CourseDTOMapper;
import edu.hogwarts.springhogwarts.dto.student.StudentDTOMapper;
import edu.hogwarts.springhogwarts.dto.student.request.StudentDTOIdsList;
import edu.hogwarts.springhogwarts.dto.teacher.TeacherDTOMapper;
import edu.hogwarts.springhogwarts.models.Course;
import edu.hogwarts.springhogwarts.models.Student;
import edu.hogwarts.springhogwarts.repositories.CourseRepository;
import edu.hogwarts.springhogwarts.repositories.StudentRepository;
import edu.hogwarts.springhogwarts.repositories.TeacherRepository;
import jakarta.persistence.EntityNotFoundException;
import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private TeacherRepository teacherRepository;

    @Mock
    private CourseDTOMapper courseDTOMapper;

    @Mock
    private StudentDTOMapper studentDTOMapper;

    @Mock
    private TeacherDTOMapper teacherDTOMapper;

    private CourseService courseService;

    @BeforeEach
    public void setUp() {
        courseService = new CourseService(courseRepository, teacherRepository, studentRepository, courseDTOMapper, studentDTOMapper, teacherDTOMapper);
    }


    @Test
    public void testAssignStudentsToCourse_Success() throws BadRequestException {
        Course course = new Course();
        Student student = new Student();

        long courseId = 1L;
        long studentId = 100L;

        course.setId(courseId);
        student.setId(studentId);
        student.setSchoolYear(2020);
        course.setSchoolyear(2020);

        StudentDTOIdsList studentDTOIdsList = new StudentDTOIdsList(List.of(studentId));

        // Stubbing repository methods
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));

        // Calling the method under test
        courseService.AssignStudentsToCourse(courseId, studentDTOIdsList);

        // Verifying behavior
        ArgumentCaptor<Course> courseArgumentCaptor = ArgumentCaptor.forClass(Course.class);
        verify(courseRepository, times(1)).save(courseArgumentCaptor.capture());

        // Asserting the captured values
        assertEquals(courseId, courseArgumentCaptor.getValue().getId());
        assertEquals(1, courseArgumentCaptor.getValue().getStudents().size());
        assertTrue(courseArgumentCaptor.getValue().getStudents().contains(student));
    }

    @Test
    public void testAssignStudentsToCourse_StudentSchoolYearMismatch() {
        Course course = new Course();
        Student student = new Student();

        long courseId = 1L;
        long studentId = 100L;
        course.setId(courseId);
        student.setId(studentId);

        StudentDTOIdsList studentDTOIdsList = new StudentDTOIdsList(Arrays.asList(studentId));
        student.setSchoolYear(2023);

        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));

        assertThrows(BadRequestException.class, () -> {
            courseService.AssignStudentsToCourse(courseId, studentDTOIdsList);
        });
        verify(courseRepository, never()).save(any());
    }
}
