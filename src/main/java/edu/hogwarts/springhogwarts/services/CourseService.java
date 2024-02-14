package edu.hogwarts.springhogwarts.services;

import edu.hogwarts.springhogwarts.models.Course;
import edu.hogwarts.springhogwarts.repositories.CourseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

    public List<Course> getCourses() {
        return courseRepository.findAll();
    }

    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }

    @Transactional
    public Optional<Course> updateCourse(Course course, long id) {
        Optional<Course> courseInDb = courseRepository.findById(id);

        if (courseInDb.isEmpty()) return courseInDb;

        courseInDb.get().setCurrent(course.isCurrent());
        courseInDb.get().setSchoolyear(course.getSchoolyear());
        courseInDb.get().setSubject(course.getSubject());

        return courseInDb;
    }

    public void deleteCourse(long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Could not find course"));

        courseRepository.delete(course);
    }
}
