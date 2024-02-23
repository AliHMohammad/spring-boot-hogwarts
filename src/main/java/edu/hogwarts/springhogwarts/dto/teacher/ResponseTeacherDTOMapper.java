package edu.hogwarts.springhogwarts.dto.teacher;

import edu.hogwarts.springhogwarts.models.Teacher;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class ResponseTeacherDTOMapper implements Function<Teacher, ResponseTeacherDTO> {
    @Override
    public ResponseTeacherDTO apply(Teacher teacher) {
        return new ResponseTeacherDTO(
                teacher.getId(),
                teacher.getFirstName(),
                teacher.getMiddleName(),
                teacher.getLastName(),
                teacher.getDateOfBirth(),
                teacher.isHeadOfHouse(),
                teacher.getEmploymentToString(),
                teacher.getEmploymentStart(),
                teacher.getEmploymentEnd(),
                teacher.getHouse() == null ? null : teacher.getHouse().getName()

        );
    }
}
