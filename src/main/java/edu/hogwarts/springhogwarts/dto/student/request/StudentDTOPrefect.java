package edu.hogwarts.springhogwarts.dto.student.request;

import jakarta.validation.constraints.NotNull;

public record StudentDTOPrefect(

        @NotNull(message = "prefect must not be null")
        boolean prefect
) {
}
