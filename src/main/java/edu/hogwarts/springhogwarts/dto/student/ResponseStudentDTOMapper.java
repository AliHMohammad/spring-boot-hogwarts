package edu.hogwarts.springhogwarts.dto.student;

import edu.hogwarts.springhogwarts.models.Student;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class ResponseStudentDTOMapper implements Function<Student, ResponseStudentDTO> {
    @Override
    public ResponseStudentDTO apply(Student student) {
        return new ResponseStudentDTO(
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
                student.getHouse() == null ? null : student.getHouse().getName()
        );
    }
}
