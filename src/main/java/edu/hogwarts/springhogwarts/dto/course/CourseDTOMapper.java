package edu.hogwarts.springhogwarts.dto.course;

import edu.hogwarts.springhogwarts.dto.course.CourseDTO;
import edu.hogwarts.springhogwarts.dto.student.StudentDTOMapper;
import edu.hogwarts.springhogwarts.dto.teacher.TeacherDTOMapper;
import edu.hogwarts.springhogwarts.models.Course;
import org.springframework.stereotype.Service;

import java.util.function.Function;


@Service
public class CourseDTOMapper implements Function<Course, CourseDTO> {

    private final StudentDTOMapper studentDTOMapper;
    private final TeacherDTOMapper teacherDTOMapper;

    public CourseDTOMapper(StudentDTOMapper studentDTOMapper, TeacherDTOMapper teacherDTOMapper) {
        this.studentDTOMapper = studentDTOMapper;
        this.teacherDTOMapper = teacherDTOMapper;
    }

    @Override
    public CourseDTO apply(Course course) {
        return new CourseDTO(
                course.getId(),
                course.getSubject(),
                course.getSchoolyear(),
                course.isCurrent(),
                course.getStudents()
                        .stream()
                        .map(studentDTOMapper)
                        .toList(),
                course.getTeacher() == null ? null : teacherDTOMapper.apply(course.getTeacher())
        );
    }
}


