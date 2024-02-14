package edu.hogwarts.springhogwarts.controllers;

import edu.hogwarts.springhogwarts.models.Course;
import edu.hogwarts.springhogwarts.models.Student;
import edu.hogwarts.springhogwarts.models.Teacher;
import edu.hogwarts.springhogwarts.services.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.*;

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

        return ResponseEntity.of(course);
    }

    @GetMapping("/{courseId}/teachers")
    public ResponseEntity<Object> getTeacherAssignedToCourse(@PathVariable("courseId") long courseId) {
        try {
            Teacher teacher = courseService.getTeacherAssignedToCourse(courseId);

            return ResponseEntity.ok(teacher);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }



    @GetMapping("/{courseId}/students")
    public ResponseEntity<Object> getStudentsEnrolledInCourse(@PathVariable("courseId") long courseId) {
        try {
            List<Student> students = courseService.getStudentsEnrolledInCourse(courseId);

            return ResponseEntity.ok(students);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
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

        return ResponseEntity.of(updatedCourse);
    }

    @PutMapping("/{courseId}/teachers/{teacherId}")
    public ResponseEntity<Object> assignTeacherToCourse(@PathVariable("courseId") long courseId, @PathVariable("teacherId") long teacherId) {
        try {
            return ResponseEntity.ok(courseService.assignTeacherToCourse(courseId, teacherId));
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{courseId}/students/{studentId}")
    public ResponseEntity<Object> enrollStudentInCourse(@PathVariable("courseId") long courseId, @PathVariable("studentId") long studentId) {
        try {
            return ResponseEntity.ok(courseService.enrollStudentToCourse(courseId, studentId));
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }



    @DeleteMapping("/{courseId}")
    public ResponseEntity<Course> deleteCourse(@PathVariable("courseId") long id) {
        try {
            courseService.deleteCourse(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{courseId}/teachers/{teacherId}")
    public ResponseEntity<Object> removeTeacherFromCourse(@PathVariable("courseId") long courseId, @PathVariable("teacherId") long teacherId) {
        try {
            Course course = courseService.removeTeacherFromCourse(courseId, teacherId);
            return ResponseEntity.ok(course);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{courseId}/students/{studentId}")
    public ResponseEntity<Object> removeStudentFromCourse(@PathVariable("courseId") long courseId, @PathVariable("studentId") long studentId) {
        try {
            Course course = courseService.removeStudentFromCourse(courseId, studentId);
            return ResponseEntity.ok(course);
        } catch(Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }


}
