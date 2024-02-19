package edu.hogwarts.springhogwarts.controllers;

import edu.hogwarts.springhogwarts.dto.CourseDTO;
import edu.hogwarts.springhogwarts.models.Course;
import edu.hogwarts.springhogwarts.models.Student;
import edu.hogwarts.springhogwarts.models.Teacher;
import edu.hogwarts.springhogwarts.services.CourseService;
import jakarta.validation.Valid;
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
    public ResponseEntity<List<CourseDTO>> getCourses() {
        return ResponseEntity.ok(courseService.getCourses());
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<Course> getSingleCourse(@PathVariable("courseId") long id) {
        return ResponseEntity.of(courseService.getSingleCourse(id));
    }

    @GetMapping("/{courseId}/teachers")
    public ResponseEntity<Object> getTeacherAssignedToCourse(@PathVariable("courseId") long courseId) {
        Teacher teacher = courseService.getTeacherAssignedToCourse(courseId);
        return ResponseEntity.ok(teacher);
    }

    @GetMapping("/{courseId}/students")
    public ResponseEntity<Object> getStudentsEnrolledInCourse(@PathVariable("courseId") long courseId) {
        List<Student> students = courseService.getStudentsEnrolledInCourse(courseId);
        return ResponseEntity.ok(students);
    }


    @PostMapping
    public ResponseEntity<Course> createCourse(@Valid @RequestBody Course course) {
        Course createdCourse = courseService.createCourse(course);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdCourse.getId())
                .toUri();

        return ResponseEntity.created(location).body(createdCourse);
    }

    @PutMapping("/{courseId}")
    public ResponseEntity<Course> updateCourse(@Valid @RequestBody Course course, @PathVariable("courseId") long id) {
        Course updatedCourse = courseService.updateCourse(course, id);
        return ResponseEntity.ok(updatedCourse);
    }

    @PutMapping("/{courseId}/teachers/{teacherId}")
    public ResponseEntity<Object> assignTeacherToCourse(@PathVariable("courseId") long courseId, @PathVariable("teacherId") long teacherId) {
        return ResponseEntity.ok(courseService.assignTeacherToCourse(courseId, teacherId));
    }

    @PutMapping("/{courseId}/students/{studentId}")
    public ResponseEntity<Object> enrollStudentInCourse(@PathVariable("courseId") long courseId, @PathVariable("studentId") long studentId) {
        return ResponseEntity.ok(courseService.enrollStudentToCourse(courseId, studentId));
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<Course> deleteCourse(@PathVariable("courseId") long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{courseId}/teachers/{teacherId}")
    public ResponseEntity<Object> removeTeacherFromCourse(@PathVariable("courseId") long courseId, @PathVariable("teacherId") long teacherId) {
        Course course = courseService.removeTeacherFromCourse(courseId, teacherId);
        return ResponseEntity.ok(course);
    }

    @DeleteMapping("/{courseId}/students/{studentId}")
    public ResponseEntity<Object> removeStudentFromCourse(@PathVariable("courseId") long courseId, @PathVariable("studentId") long studentId) {
        Course course = courseService.removeStudentFromCourse(courseId, studentId);
        return ResponseEntity.ok(course);
    }


}
