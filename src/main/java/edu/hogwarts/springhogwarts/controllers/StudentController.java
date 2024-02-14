package edu.hogwarts.springhogwarts.controllers;

import edu.hogwarts.springhogwarts.models.Student;
import edu.hogwarts.springhogwarts.services.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }


    @GetMapping
    public ResponseEntity<List<Student>> getStudents() {
        return new ResponseEntity<>(studentService.getStudents(), HttpStatus.OK);
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<Student> getSingleStudent(@PathVariable("studentId") long id) {

        Optional<Student> studentFound = studentService.getSingleStudent(id);

        if (studentFound.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(studentFound.get());
    }

    @PostMapping
    public ResponseEntity<Student> registerNewStudent(@RequestBody Student student) {
        System.out.println(student);

        Student createdStudent = studentService.addNewStudent(student);

        //Vi bygger en location til response header
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdStudent.getId())
                .toUri();

        return ResponseEntity.created(location).body(createdStudent);

    }

    @DeleteMapping(path = "/{studentId}")
    public ResponseEntity<Student> deleteStudent(@PathVariable("studentId") long id) {
        Optional<Student> studentDeleted = studentService.deleteStudent(id);

        if (studentDeleted.isEmpty()) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(studentDeleted.get());
    }

    @PutMapping(path = "/{studentId}")
    public ResponseEntity<Student> updateStudent(@PathVariable("studentId") long id, @RequestBody Student updatedStudent) {
        Optional<Student> student = studentService.updateStudent(id, updatedStudent);

        if (student.isEmpty()) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(student.get());
    }
}
