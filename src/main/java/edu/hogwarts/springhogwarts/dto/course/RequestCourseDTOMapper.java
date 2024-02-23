package edu.hogwarts.springhogwarts.dto.course;

import edu.hogwarts.springhogwarts.models.Course;
import edu.hogwarts.springhogwarts.models.Student;
import edu.hogwarts.springhogwarts.models.Teacher;
import edu.hogwarts.springhogwarts.repositories.StudentRepository;
import edu.hogwarts.springhogwarts.repositories.TeacherRepository;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.Optional;
import java.util.function.Function;

@ControllerAdvice
public class RequestCourseDTOMapper implements Function<RequestCourseDTO, Course> {

    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;

    public RequestCourseDTOMapper(StudentRepository studentRepository, TeacherRepository teacherRepository) {
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
    }

    @Override
    public Course apply(RequestCourseDTO requestCourseDTO) {
        Course course = new Course();
        course.setId(requestCourseDTO.id());
        course.setSubject(requestCourseDTO.subject());
        course.setSchoolyear(requestCourseDTO.schoolyear());
        course.setCurrent(requestCourseDTO.current());

        if (requestCourseDTO.teacher() != null) {
            Teacher t = teacherRepository.findById(requestCourseDTO.teacher())
                    .orElse(null);
            course.setTeacher(t);
        }

        if (requestCourseDTO.students() != null) {
            for (Long id: requestCourseDTO.students()) {
                Student s = studentRepository.findById(id)
                        .orElse(null);
                course.assignStudent(s);
            }
        }

        return course;
    }
}
