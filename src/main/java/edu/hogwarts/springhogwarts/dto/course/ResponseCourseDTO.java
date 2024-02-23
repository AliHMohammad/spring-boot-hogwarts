package edu.hogwarts.springhogwarts.dto.course;

import edu.hogwarts.springhogwarts.dto.student.ResponseStudentDTO;
import edu.hogwarts.springhogwarts.dto.teacher.ResponseTeacherDTO;

import java.util.List;

public record ResponseCourseDTO(
        Long id,
        String subject,
        int schoolyear,
        boolean current,
        List<ResponseStudentDTO> students,
        ResponseTeacherDTO teacher
) {

}


