package edu.hogwarts.springhogwarts.dto.teacher;


import java.time.LocalDate;

public record ResponseTeacherDTO(
        Long id,
        String firstName,
        String middleName,
        String lastName,
        LocalDate dateOfBirth,
        boolean headOfHouse,
        String employment,
        LocalDate employmentStart,
        LocalDate employmentEnd,
        String house

) {
}
