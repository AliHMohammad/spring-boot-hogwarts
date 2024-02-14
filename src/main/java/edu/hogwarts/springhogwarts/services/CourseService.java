package edu.hogwarts.springhogwarts.services;

import edu.hogwarts.springhogwarts.models.Course;
import edu.hogwarts.springhogwarts.repositories.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }


    public Optional<Course> getSingleCourse(long id) {
        return courseRepository.findById(id);
    }
}
