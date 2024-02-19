package edu.hogwarts.springhogwarts.dto.teacher.request;

import edu.hogwarts.springhogwarts.models.EmploymentType;
import jakarta.validation.constraints.NotNull;

public record TeacherDTOEmployment(

        @NotNull(message = "Employment must not be null")
        EmploymentType employment
) {
}
