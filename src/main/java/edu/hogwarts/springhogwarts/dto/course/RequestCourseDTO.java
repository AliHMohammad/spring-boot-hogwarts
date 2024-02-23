package edu.hogwarts.springhogwarts.dto.course;

import edu.hogwarts.springhogwarts.models.Student;
import edu.hogwarts.springhogwarts.models.Teacher;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.util.HashSet;
import java.util.Set;

public record RequestCourseDTO(
        Long id,
        @NotBlank(message = "subject must not be blank")
        String subject,
        @Min(value = 1, message = "schoolyear must be minimum 1")
        @Max(value = 7, message = "schoolyear must be maximum 7")
        int schoolyear,
        boolean current,
        Long teacher,
        Set<Long> students
) {
}
