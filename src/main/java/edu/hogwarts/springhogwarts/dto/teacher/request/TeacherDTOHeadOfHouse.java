package edu.hogwarts.springhogwarts.dto.teacher.request;

import jakarta.validation.constraints.NotNull;

public record TeacherDTOHeadOfHouse(

        @NotNull(message = "headOfHouse must not be null")
        boolean headOfHouse
) {
}
