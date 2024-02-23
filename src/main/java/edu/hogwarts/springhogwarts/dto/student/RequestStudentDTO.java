package edu.hogwarts.springhogwarts.dto.student;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;

public record RequestStudentDTO(
        Long id,
        String firstName,
        String middleName,
        String lastName,
        String fullName,

        @Past(message = "dateOfBirth must be in the past time")
        @NotNull(message = "dateOfBirth must not be null")
        LocalDate dateOfBirth,

        @Min(value = 1, message = "Minimum schoolYear is 1")
        @Max(value = 7, message = "Maximum schoolYear is 7")
        int schoolYear,

        @NotNull(message = "prefect must not be null")
        boolean prefect,
        int enrollmentYear,
        Integer graduationYear,
        boolean graduated,
        String house
) {

    public RequestStudentDTO {
        if (fullName != null) {
            String[] parts = fullName.split(" ");
            firstName = parts[0];
            lastName = parts[parts.length - 1];

            if (parts.length > 2) middleName = parts[1];
        }
    }
}
