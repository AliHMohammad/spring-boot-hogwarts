package edu.hogwarts.springhogwarts.services;

import edu.hogwarts.springhogwarts.models.Student;
import edu.hogwarts.springhogwarts.repositories.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class StudentServiceTest {

    @Autowired
    private StudentRepository studentRepository;


    @BeforeEach
    void setUp() {
        studentRepository.save(new Student("Ali Haider Mohammad", LocalDate.of(2000, 9, 12), false, 2008, 2016, true));
        studentRepository.save(new Student("Berfin Flora Turan", LocalDate.of(2001, 6,11), true, 2010, 2018, true));
    }

    @Test
    public void getAllStudents() {
        //assertEquals(2, studentService.getStudents().size());
        //assertEquals(2, studentRepository.count());
    }
}