package edu.hogwarts.springhogwarts.dto.student;

import edu.hogwarts.springhogwarts.dto.student.StudentDTO;
import edu.hogwarts.springhogwarts.models.Student;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class StudentDTOMapper implements Function<Student, StudentDTO> {
    @Override
    public StudentDTO apply(Student student) {
        return new StudentDTO(
                student.getId(),
                student.getFirstName(),
                student.getMiddleName(),
                student.getLastName(),
                student.getDateOfBirth(),
                student.getSchoolYear(),
                student.isPrefect(),
                student.getEnrollmentYear(),
                student.getGraduationYear(),
                student.isGraduated(),
                student.getHouse().getName()
        );
    }
}
