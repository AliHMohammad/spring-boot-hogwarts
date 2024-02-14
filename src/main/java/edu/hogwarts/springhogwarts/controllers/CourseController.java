package edu.hogwarts.springhogwarts.controllers;

import edu.hogwarts.springhogwarts.models.Course;
import edu.hogwarts.springhogwarts.services.CourseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/courses")
public class CourseController {

    private CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public ResponseEntity<List<Course>> getCourses() {
        return ResponseEntity.ok(courseService.getCourses());
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<Course> getSingleCourse(@PathVariable("courseId") long id) {
        Optional<Course> course = courseService.getSingleCourse(id);

        if (course.isEmpty()) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(course.get());
    }

    @PostMapping
    public ResponseEntity<Course> createCourse(@RequestBody Course course) {
        Course createdCourse = courseService.createCourse(course);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdCourse.getId())
                .toUri();

        return ResponseEntity.created(location).body(createdCourse);
    }

    @PutMapping("/{courseId}")
    public ResponseEntity<Course> updateCourse(@RequestBody Course course, @PathVariable("courseId") long id) {
        Optional<Course> updatedCourse = courseService.updateCourse(course, id);

        if (updatedCourse.isEmpty()) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(updatedCourse.get());
    }

    //PUT teacher and student in course

    //DELETE teacher, student from course

    @DeleteMapping("/{courseId}")
    public ResponseEntity<Course> deleteCourse(@PathVariable("courseId") long id) {
        try {
            courseService.deleteCourse(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
