package edu.hogwarts.springhogwarts.dto.course;

import edu.hogwarts.springhogwarts.dto.student.ResponseStudentDTOMapper;
import edu.hogwarts.springhogwarts.dto.teacher.ResponseTeacherDTOMapper;
import edu.hogwarts.springhogwarts.models.Course;
import org.springframework.stereotype.Service;

import java.util.function.Function;


@Service
public class ResponseCourseDTOMapper implements Function<Course, ResponseCourseDTO> {

    private final ResponseStudentDTOMapper responseStudentDTOMapper;
    private final ResponseTeacherDTOMapper responseTeacherDTOMapper;

    public ResponseCourseDTOMapper(ResponseStudentDTOMapper responseStudentDTOMapper, ResponseTeacherDTOMapper responseTeacherDTOMapper) {
        this.responseStudentDTOMapper = responseStudentDTOMapper;
        this.responseTeacherDTOMapper = responseTeacherDTOMapper;
    }

    @Override
    public ResponseCourseDTO apply(Course course) {
        return new ResponseCourseDTO(
                course.getId(),
                course.getSubject(),
                course.getSchoolyear(),
                course.isCurrent(),
                course.getStudents()
                        .stream()
                        .map(responseStudentDTOMapper)
                        .toList(),
                course.getTeacher() == null ? null : responseTeacherDTOMapper.apply(course.getTeacher())
        );
    }
}


