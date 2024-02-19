package edu.hogwarts.springhogwarts.controllers;

import edu.hogwarts.springhogwarts.dto.course.CourseDTO;
import edu.hogwarts.springhogwarts.dto.student.StudentDTO;
import edu.hogwarts.springhogwarts.dto.student.request.StudentDTOIdMap;
import edu.hogwarts.springhogwarts.dto.teacher.TeacherDTO;
import edu.hogwarts.springhogwarts.dto.teacher.request.TeacherDTOId;
import edu.hogwarts.springhogwarts.models.Course;
import edu.hogwarts.springhogwarts.models.Student;
import edu.hogwarts.springhogwarts.models.Teacher;
import edu.hogwarts.springhogwarts.services.CourseService;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
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
    public ResponseEntity<CourseDTO> getSingleCourse(@PathVariable("courseId") long id) {
        return ResponseEntity.ok(courseService.getSingleCourse(id));
    }

    @GetMapping("/{courseId}/teachers")
    public ResponseEntity<TeacherDTO> getTeacherAssignedToCourse(@PathVariable("courseId") long courseId) {
        return ResponseEntity.ok(courseService.getTeacherAssignedToCourse(courseId));
    }

    @GetMapping("/{courseId}/students")
    public ResponseEntity<List<StudentDTO>> getStudentsEnrolledInCourse(@PathVariable("courseId") long courseId) {
        return ResponseEntity.ok(courseService.getStudentsEnrolledInCourse(courseId));
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
    public ResponseEntity<CourseDTO> updateCourse(@Valid @RequestBody Course course, @PathVariable("courseId") long id) {
        return ResponseEntity.ok(courseService.updateCourse(course, id));
    }


    @DeleteMapping("/{courseId}")
    public ResponseEntity<CourseDTO> deleteCourse(@PathVariable("courseId") long id) {
        return ResponseEntity.ok(courseService.deleteCourse(id));
    }


    @DeleteMapping("/{courseId}/students/{studentId}")
    public ResponseEntity<CourseDTO> removeStudentFromCourse(@PathVariable("courseId") long courseId, @PathVariable("studentId") long studentId) {
        return ResponseEntity.ok(courseService.removeStudentFromCourse(courseId, studentId));
    }

    @PatchMapping("/{courseId}/teachers")
    public ResponseEntity<CourseDTO> updateTeacherInCourse(@PathVariable("courseId") long courseId, @RequestBody TeacherDTOId teacherDTOId) {
        return ResponseEntity.ok(courseService.updateTeacherInCourse(courseId, teacherDTOId));
    }

    @PostMapping("/{courseId}/students")
    public ResponseEntity<CourseDTO> AssignStudentsToCourse(
            @PathVariable("courseId") long courseId,
            @RequestBody StudentDTOIdMap studentDTOIdMap)
            throws BadRequestException
    {
        return ResponseEntity.ok(courseService.AssignStudentsToCourse(courseId, studentDTOIdMap));
    }

}
