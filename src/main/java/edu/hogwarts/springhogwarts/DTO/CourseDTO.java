package edu.hogwarts.springhogwarts.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record CourseDTO(
        Long id,
        String subject,
        int schoolyear,
        boolean current,
        Long teacherId,
        List<Long> studentIds
) {

}


