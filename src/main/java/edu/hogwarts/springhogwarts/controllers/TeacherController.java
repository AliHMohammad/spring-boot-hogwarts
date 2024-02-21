package edu.hogwarts.springhogwarts.controllers;

import edu.hogwarts.springhogwarts.dto.teacher.TeacherDTO;
import edu.hogwarts.springhogwarts.dto.teacher.request.TeacherDTOEmployment;
import edu.hogwarts.springhogwarts.dto.teacher.request.TeacherDTOEmploymentEnd;
import edu.hogwarts.springhogwarts.dto.teacher.request.TeacherDTOHeadOfHouse;
import edu.hogwarts.springhogwarts.models.Teacher;
import edu.hogwarts.springhogwarts.services.TeacherService;
import jakarta.validation.Valid;
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
    public ResponseEntity<List<TeacherDTO>> getTeachers() {
        return ResponseEntity.ok(teacherService.getTeachers());
    }

    @GetMapping("/{teacherId}")
    public ResponseEntity<TeacherDTO> getSingleTeacher(@PathVariable("teacherId") long id) {
        return ResponseEntity.ok(teacherService.getSingleTeacher(id));
    }

    @PostMapping
    public ResponseEntity<TeacherDTO> registerNewTeacher(@Valid @RequestBody Teacher teacher) {
        TeacherDTO createdTeacher = teacherService.createTeacher(teacher);

        //Vi bygger en location til response header
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdTeacher.id())
                .toUri();

        return ResponseEntity.created(location).body(createdTeacher);
    }

    @DeleteMapping(path = "/{teacherId}")
    public ResponseEntity<TeacherDTO> deleteTeacher(@PathVariable("teacherId") long id) {
        return ResponseEntity.ok(teacherService.deleteTeacher(id));
    }

    @PutMapping(path = "/{teacherId}")
    public ResponseEntity<TeacherDTO> updateTeacher(@PathVariable("teacherId") long id, @Valid @RequestBody Teacher teacher) {
        return ResponseEntity.ok(teacherService.updateTeacher(id, teacher));
    }

    @PatchMapping("/{teacherId}/headofhouse")
    public ResponseEntity<TeacherDTO> updateTeacherHeadOfHouse(
            @Valid @RequestBody TeacherDTOHeadOfHouse teacherDTOHeadOfHouse,
            @PathVariable("teacherId") long id
    ) {
        return ResponseEntity.ok(teacherService.updateTeacherHeadOfHouse(teacherDTOHeadOfHouse, id));
    }

    @PatchMapping("/{teacherId}/employmentend")
    public ResponseEntity<TeacherDTO> updateTeacherEmploymentEnd(
            @Valid @RequestBody TeacherDTOEmploymentEnd teacherDTOEmploymentEnd,
            @PathVariable("teacherId") long id
    ) {
        return ResponseEntity.ok(teacherService.updateTeacherEmploymentEnd(teacherDTOEmploymentEnd, id));
    }


    @PatchMapping("/{teacherId}/employment")
    public ResponseEntity<TeacherDTO> updateTeacherEmployment(
            @Valid @RequestBody TeacherDTOEmployment teacherDTOEmployment,
            @PathVariable("teacherId") long id
    ) {
        return ResponseEntity.ok(teacherService.updateTeacherEmployment(teacherDTOEmployment, id));
    }
}
