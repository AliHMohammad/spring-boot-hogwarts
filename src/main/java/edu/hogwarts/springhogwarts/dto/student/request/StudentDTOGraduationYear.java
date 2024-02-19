package edu.hogwarts.springhogwarts.dto.student.request;

import jakarta.validation.constraints.NotNull;

public record StudentDTOGraduationYear(
        Integer graduationYear
) {
}
