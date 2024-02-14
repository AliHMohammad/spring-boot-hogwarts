package edu.hogwarts.springhogwarts.controllers;

import edu.hogwarts.springhogwarts.models.Teacher;
import edu.hogwarts.springhogwarts.services.TeacherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

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

    @GetMapping("/{teacherId}")
    public ResponseEntity<Teacher> getSingleTeacher(@PathVariable("teacherId") long id) {
        Optional<Teacher> teacherInDb = teacherService.getSingleTeacher(id);

        return ResponseEntity.of(teacherInDb);
    }

    @PostMapping
    public ResponseEntity<Teacher> registerNewTeacher(@RequestBody Teacher teacher) {
        Teacher createdTeacher = teacherService.createTeacher(teacher);

        //Vi bygger en location til response header
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdTeacher.getId())
                .toUri();

        return ResponseEntity.created(location).body(createdTeacher);
    }

    @DeleteMapping(path = "/{teacherId}")
    public ResponseEntity<Teacher> deleteTeacher(@PathVariable("teacherId") long id) {
        Optional<Teacher> teacherDeleted = teacherService.deleteTeacher(id);

        return ResponseEntity.of(teacherDeleted);
    }

    @PutMapping(path = "/{teacherId}")
    public ResponseEntity<Teacher> updateTeacher(@PathVariable("teacherId") long id, @RequestBody Teacher teacher) {
        Optional<Teacher> updatedTeacher = teacherService.updateTeacher(id, teacher);

        return ResponseEntity.of(updatedTeacher);
    }
}
