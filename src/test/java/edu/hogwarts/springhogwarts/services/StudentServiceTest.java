package edu.hogwarts.springhogwarts.services;


import edu.hogwarts.springhogwarts.models.Student;
import edu.hogwarts.springhogwarts.repositories.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    //altid mock repository-laget med @Mock
    @Mock
    private StudentRepository studentRepository;
    private StudentService studentService;

    @BeforeEach
    void setUp() {
        //vores service-lag, som vi tester, tager den mock vi laver som parameter
        studentService = new StudentService(studentRepository);
    }

    @Test
    void getAllStudents() {
        //when
        studentService.getStudents();
        //then
        Mockito.verify(studentRepository, times(1)).findAll();
    }

    @Test
    void addStudent() {
        //given
        Student student = new Student();
        student.setFullName("Lars Møller");
        student.setDateOfBirth(LocalDate.of(1990,4,1));

        //when
        studentService.addNewStudent(student);

        //then
        ArgumentCaptor<Student> studentArgumentCaptor =
                ArgumentCaptor.forClass(Student.class);

        verify(studentRepository, times(1)).save(studentArgumentCaptor.capture());
        //import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
        assertThat(studentArgumentCaptor.getValue()).isEqualTo(student);
    }

    @Test
    @Disabled
    void DeleteStudent() {
        //when
        studentService.deleteStudent(1);
        //then
    }
}

