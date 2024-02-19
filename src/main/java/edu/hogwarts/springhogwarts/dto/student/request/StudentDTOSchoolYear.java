package edu.hogwarts.springhogwarts.dto.student.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record StudentDTOSchoolYear(

        @Min(value = 1, message = "Minimum schoolYear is 1")
        @Max(value = 7, message = "Maximum schoolYear is 7")
        int schoolYear
) {
}
