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

        //when
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        when(courseDTOMapper.apply(course)).thenReturn(courseDTOMapper.apply(course));
        //CourseDTO result = courseService.AssignStudentsToCourse(courseId, studentDTOIdsList);

        ArgumentCaptor<CourseDTO> courseDTOArgumentCaptor = ArgumentCaptor.forClass(CourseDTO.class);

        //then
        assertEquals(courseId, courseDTOArgumentCaptor.capture().id());
        assertEquals(1, courseDTOArgumentCaptor.capture().students().size());
        assertTrue(course.getStudents().contains(student));
        verify(courseRepository, times(1)).save(course);
    }


    public void testAssignStudentsToCourse_CourseNotFound() {
        long courseId = 1L;
        StudentDTOIdsList studentDTOIdsList = new StudentDTOIdsList(Collections.singletonList(100L));

        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            courseService.AssignStudentsToCourse(courseId, studentDTOIdsList);
        });
        verify(studentRepository, never()).findById(anyLong());
        verify(courseRepository, never()).save(any());
    }


    public void testAssignStudentsToCourse_StudentNotFound() {
        long courseId = 1L;
        long studentId = 100L;
        Course course = new Course();
        course.setId(courseId);
        StudentDTOIdsList studentDTOIdsList = new StudentDTOIdsList(Arrays.asList(studentId));

        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        when(studentRepository.findById(studentId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            courseService.AssignStudentsToCourse(courseId, studentDTOIdsList);
        });
        verify(courseRepository, never()).save(any());
    }


    public void testAssignStudentsToCourse_StudentSchoolYearMismatch() {
        long courseId = 1L;
        long studentId = 100L;
        Course course = new Course();
        course.setId(courseId);
        StudentDTOIdsList studentDTOIdsList = new StudentDTOIdsList(Arrays.asList(studentId));
        Student student = new Student();
        student.setId(studentId);
        student.setSchoolYear(2023);

        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));

        assertThrows(BadRequestException.class, () -> {
            courseService.AssignStudentsToCourse(courseId, studentDTOIdsList);
        });
        verify(courseRepository, never()).save(any());
    }
}
