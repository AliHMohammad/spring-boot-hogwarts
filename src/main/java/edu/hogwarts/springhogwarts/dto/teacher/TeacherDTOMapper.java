package edu.hogwarts.springhogwarts.dto.teacher;

import edu.hogwarts.springhogwarts.models.Teacher;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class TeacherDTOMapper implements Function<Teacher, TeacherDTO> {
    @Override
    public TeacherDTO apply(Teacher teacher) {
        return new TeacherDTO(
                teacher.getId(),
                teacher.getFirstName(),
                teacher.getMiddleName(),
                teacher.getLastName(),
                teacher.getDateOfBirth(),
                teacher.isHeadOfHouse(),
                teacher.getEmploymentToString(),
                teacher.getEmploymentStart(),
                teacher.getEmploymentEnd(),
                teacher.getHouse().getName()

        );
    }
}
