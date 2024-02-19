package edu.hogwarts.springhogwarts.dto.course;

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


