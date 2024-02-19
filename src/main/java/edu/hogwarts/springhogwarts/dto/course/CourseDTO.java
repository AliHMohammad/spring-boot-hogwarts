package edu.hogwarts.springhogwarts.dto.course;

import edu.hogwarts.springhogwarts.dto.student.StudentDTO;
import edu.hogwarts.springhogwarts.dto.teacher.TeacherDTO;

import java.util.List;

public record CourseDTO(
        Long id,
        String subject,
        int schoolyear,
        boolean current,
        List<StudentDTO> students,
        TeacherDTO teacher
) {

}


