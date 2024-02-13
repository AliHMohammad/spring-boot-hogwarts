package edu.hogwarts.springhogwarts.controllers;

import edu.hogwarts.springhogwarts.models.Teacher;
import edu.hogwarts.springhogwarts.services.TeacherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/teachers")
public class TeacherController {

    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping
    public ResponseEntity<List<Teacher>> getTeachers() {
        return ResponseEntity.ok(teacherService.getTeachers());
    }

    /*@GetMapping("/{teacherId}")
    public ResponseEntity<Teacher> getSingleTeacher(@PathVariable("teacherId") long id) {
        try {

        } catch (Exception e) {

        }

    }*/
}
