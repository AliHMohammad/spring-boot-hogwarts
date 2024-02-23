package edu.hogwarts.springhogwarts.dto.teacher;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;

public record RequestTeacherDTO(
        Long id,
        String firstName,
        String middleName,
        String lastName,
        String fullName,

        @Past(message = "dateOfBirth must be in the past time")
        @NotNull(message = "dateOfBirth must not be null")
        LocalDate dateOfBirth,
        boolean headOfHouse,

        @NotBlank(message = "employment must not be blank")
        String employment,
        LocalDate employmentStart,
        LocalDate employmentEnd,
        String house
) {

    public RequestTeacherDTO {
        if (fullName != null) {
            String[] parts = fullName.split(" ");
            firstName = parts[0];
            lastName = parts[parts.length - 1];

            if (parts.length > 2) middleName = parts[1];
        }
    }
}
