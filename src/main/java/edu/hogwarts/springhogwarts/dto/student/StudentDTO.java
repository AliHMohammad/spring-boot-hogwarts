package edu.hogwarts.springhogwarts.dto.student;

import java.time.LocalDate;

public record StudentDTO(
        Long id,
        String firstName,
        String middleName,
        String lastName,
        LocalDate dateOfBirth,
        int schoolYear,
        boolean prefect,
        int enrollmentYear,
        int graduationYear,
        boolean graduated,
        String house
) {

}
