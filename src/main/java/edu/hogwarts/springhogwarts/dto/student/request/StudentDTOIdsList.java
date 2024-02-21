package edu.hogwarts.springhogwarts.dto.student.request;

import java.util.List;

public record StudentDTOIdsList(
        List<Long> students
) {
}
