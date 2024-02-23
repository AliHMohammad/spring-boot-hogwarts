package edu.hogwarts.springhogwarts.controllers;

import edu.hogwarts.springhogwarts.dto.course.RequestCourseDTO;
import edu.hogwarts.springhogwarts.dto.course.ResponseCourseDTO;
import edu.hogwarts.springhogwarts.dto.student.ResponseStudentDTO;
import edu.hogwarts.springhogwarts.dto.student.request.StudentDTOIdsList;
import edu.hogwarts.springhogwarts.dto.student.request.StudentDTONamesList;
import edu.hogwarts.springhogwarts.dto.teacher.ResponseTeacherDTO;
import edu.hogwarts.springhogwarts.dto.teacher.request.TeacherDTOId;
import edu.hogwarts.springhogwarts.models.Course;
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
    public ResponseEntity<List<ResponseCourseDTO>> getCourses() {
        return ResponseEntity.ok(courseService.getCourses());
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<ResponseCourseDTO> getSingleCourse(@PathVariable("courseId") long id) {
        return ResponseEntity.ok(courseService.getSingleCourse(id));
    }

    @GetMapping("/{courseId}/teachers")
    public ResponseEntity<ResponseTeacherDTO> getTeacherAssignedToCourse(@PathVariable("courseId") long courseId) {
        ResponseTeacherDTO responseTeacherDTO = courseService.getTeacherAssignedToCourse(courseId);
        return responseTeacherDTO == null ? ResponseEntity.noContent().build() : ResponseEntity.ok(responseTeacherDTO);
    }

    @GetMapping("/{courseId}/students")
    public ResponseEntity<List<ResponseStudentDTO>> getStudentsEnrolledInCourse(@PathVariable("courseId") long courseId) {
        return ResponseEntity.ok(courseService.getStudentsEnrolledInCourse(courseId));
    }


    @PostMapping
    public ResponseEntity<ResponseCourseDTO> createCourse(@Valid @RequestBody RequestCourseDTO requestCourseDTO) {
        ResponseCourseDTO createdCourse = courseService.createCourse(requestCourseDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdCourse.id())
                .toUri();

        return ResponseEntity.created(location).body(createdCourse);
    }

    @PutMapping("/{courseId}")
    public ResponseEntity<ResponseCourseDTO> updateCourse(@Valid @RequestBody RequestCourseDTO requestCourseDTO, @PathVariable("courseId") long id) {
        return ResponseEntity.ok(courseService.updateCourse(requestCourseDTO, id));
    }


    @DeleteMapping("/{courseId}")
    public ResponseEntity<ResponseCourseDTO> deleteCourse(@PathVariable("courseId") long id) {
        return ResponseEntity.ok(courseService.deleteCourse(id));
    }


    @DeleteMapping("/{courseId}/students/{studentId}")
    public ResponseEntity<ResponseCourseDTO> removeStudentFromCourse(@PathVariable("courseId") long courseId, @PathVariable("studentId") long studentId) {
        return ResponseEntity.ok(courseService.removeStudentFromCourse(courseId, studentId));
    }

    @PatchMapping("/{courseId}/teachers")
    public ResponseEntity<ResponseCourseDTO> updateTeacherInCourse(@PathVariable("courseId") long courseId, @RequestBody TeacherDTOId teacherDTOId) {
        return ResponseEntity.ok(courseService.updateTeacherInCourse(courseId, teacherDTOId));
    }

    @PostMapping("/{courseId}/students")
    public ResponseEntity<ResponseCourseDTO> AssignStudentsToCourse(
            @PathVariable("courseId") long courseId,
            @RequestBody StudentDTOIdsList studentDTOIdsList)
            throws BadRequestException
    {
        return ResponseEntity.ok(courseService.AssignStudentsToCourse(courseId, studentDTOIdsList));
    }

    @PostMapping("/{courseId}/students/name")
    public ResponseEntity<ResponseCourseDTO> AssignStudentsToCourseWithNames(
            @PathVariable("courseId") long courseId,
            @RequestBody StudentDTONamesList studentDTONamesList)
            throws BadRequestException
    {
        return ResponseEntity.ok(courseService.AssignStudentsToCourseWithNames(courseId, studentDTONamesList));
    }

}
