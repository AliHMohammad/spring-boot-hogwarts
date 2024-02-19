package edu.hogwarts.springhogwarts.dto.course;

import edu.hogwarts.springhogwarts.dto.course.CourseDTO;
import edu.hogwarts.springhogwarts.models.Course;
import org.springframework.stereotype.Service;

import java.util.function.Function;


@Service
public class CourseDTOMapper implements Function<Course, CourseDTO> {
    @Override
    public CourseDTO apply(Course course) {
        return new CourseDTO(
                course.getId(),
                course.getSubject(),
                course.getSchoolyear(),
                course.isCurrent(),
                course.getTeacher().getId(),
                course.getStudents().stream().map(student -> student.getId()).toList()
        );
    }
}


