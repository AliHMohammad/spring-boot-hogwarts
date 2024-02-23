package edu.hogwarts.springhogwarts.controllers;

import edu.hogwarts.springhogwarts.dto.student.RequestStudentDTO;
import edu.hogwarts.springhogwarts.dto.student.ResponseStudentDTO;
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
    public ResponseEntity<List<ResponseStudentDTO>> getStudents() {
        return new ResponseEntity<>(studentService.getStudents(), HttpStatus.OK);
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<ResponseStudentDTO> getSingleStudent(@PathVariable("studentId") long id) {
        return ResponseEntity.ok(studentService.getSingleStudent(id));
    }

    @PostMapping
    public ResponseEntity<ResponseStudentDTO> registerNewStudent(@Valid @RequestBody RequestStudentDTO requestStudentDTO) {
        ResponseStudentDTO createdStudent = studentService.addNewStudent(requestStudentDTO);

        //Vi bygger en location til response header
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdStudent.id())
                .toUri();

        return ResponseEntity.created(location).body(createdStudent);
    }

    @DeleteMapping(path = "/{studentId}")
    public ResponseEntity<ResponseStudentDTO> deleteStudent(@PathVariable("studentId") long id) {
        return ResponseEntity.ok(studentService.deleteStudent(id));
    }

    @PutMapping(path = "/{studentId}")
    public ResponseEntity<ResponseStudentDTO> updateStudent(@PathVariable("studentId") long id, @Valid @RequestBody RequestStudentDTO updatedStudent) {
        return ResponseEntity.ok(studentService.updateStudent(id, updatedStudent));
    }


    @PatchMapping("/{studentId}/prefect")
    public ResponseEntity<ResponseStudentDTO> updateStudentPrefect(
            @Valid @RequestBody StudentDTOPrefect studentDTOPrefect,
            @PathVariable("studentId") long id
    ) {
        return ResponseEntity.ok(studentService.updateStudentPrefect(studentDTOPrefect, id));
    }

    @PatchMapping("/{studentId}/schoolyear")
    public ResponseEntity<ResponseStudentDTO> updateStudentSchoolYear(
            @Valid @RequestBody StudentDTOSchoolYear studentDTOSchoolYear,
            @PathVariable("studentId") long id
    ) {
        return ResponseEntity.ok(studentService.updateStudentSchoolYear(studentDTOSchoolYear, id));
    }

    @PatchMapping("/{studentId}/graduationyear")
    public ResponseEntity<ResponseStudentDTO> updateStudentGraduationYear(
            @Valid @RequestBody StudentDTOGraduationYear studentDTOGraduationYear,
            @PathVariable("studentId") long id
    ) {
        return ResponseEntity.ok(studentService.updateStudentGraduationYear(studentDTOGraduationYear, id));
    }
}
