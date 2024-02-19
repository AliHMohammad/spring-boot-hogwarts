package edu.hogwarts.springhogwarts.controllers;

import edu.hogwarts.springhogwarts.dto.student.StudentDTO;
import edu.hogwarts.springhogwarts.dto.student.request.StudentDTOGraduationYear;
import edu.hogwarts.springhogwarts.dto.student.request.StudentDTOPrefect;
import edu.hogwarts.springhogwarts.dto.student.request.StudentDTOSchoolYear;
import edu.hogwarts.springhogwarts.models.Student;
import edu.hogwarts.springhogwarts.services.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(path = "/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }


    @GetMapping
    public ResponseEntity<List<StudentDTO>> getStudents() {
        return new ResponseEntity<>(studentService.getStudents(), HttpStatus.OK);
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<StudentDTO> getSingleStudent(@PathVariable("studentId") long id) {
        return ResponseEntity.ok(studentService.getSingleStudent(id));
    }

    @PostMapping
    public ResponseEntity<StudentDTO> registerNewStudent(@Valid @RequestBody Student student) {
        StudentDTO createdStudent = studentService.addNewStudent(student);

        //Vi bygger en location til response header
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdStudent.id())
                .toUri();

        return ResponseEntity.created(location).body(createdStudent);
    }

    @DeleteMapping(path = "/{studentId}")
    public ResponseEntity<StudentDTO> deleteStudent(@PathVariable("studentId") long id) {
        return ResponseEntity.ok(studentService.deleteStudent(id));
    }

    @PutMapping(path = "/{studentId}")
    public ResponseEntity<StudentDTO> updateStudent(@PathVariable("studentId") long id, @Valid @RequestBody Student updatedStudent) {
        return ResponseEntity.ok(studentService.updateStudent(id, updatedStudent));
    }


    @PatchMapping("/{studentId}/prefect")
    public ResponseEntity<StudentDTO> updateStudentPrefect(
            @Valid @RequestBody StudentDTOPrefect studentDTOPrefect,
            @PathVariable("studentId") long id
    ) {
        return ResponseEntity.ok(studentService.updateStudentPrefect(studentDTOPrefect, id));
    }

    @PatchMapping("/{studentId}/schoolyear")
    public ResponseEntity<StudentDTO> updateStudentSchoolYear(
            @Valid @RequestBody StudentDTOSchoolYear studentDTOSchoolYear,
            @PathVariable("studentId") long id
    ) {
        return ResponseEntity.ok(studentService.updateStudentSchoolYear(studentDTOSchoolYear, id));
    }

    @PatchMapping("/{studentId}/graduationyear")
    public ResponseEntity<StudentDTO> updateStudentGraduationYear(
            @Valid @RequestBody StudentDTOGraduationYear studentDTOGraduationYear,
            @PathVariable("studentId") long id
    ) {
        return ResponseEntity.ok(studentService.updateStudentGraduationYear(studentDTOGraduationYear, id));
    }
}
