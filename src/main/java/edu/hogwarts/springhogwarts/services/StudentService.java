package edu.hogwarts.springhogwarts.services;

import edu.hogwarts.springhogwarts.dto.student.RequestStudentDTO;
import edu.hogwarts.springhogwarts.dto.student.RequestStudentDTOMapper;
import edu.hogwarts.springhogwarts.dto.student.ResponseStudentDTO;
import edu.hogwarts.springhogwarts.dto.student.ResponseStudentDTOMapper;
import edu.hogwarts.springhogwarts.dto.student.request.StudentDTOGraduationYear;
import edu.hogwarts.springhogwarts.dto.student.request.StudentDTOPrefect;
import edu.hogwarts.springhogwarts.dto.student.request.StudentDTOSchoolYear;
import edu.hogwarts.springhogwarts.models.Course;
import edu.hogwarts.springhogwarts.models.Student;
import edu.hogwarts.springhogwarts.repositories.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final ResponseStudentDTOMapper responseStudentDTOMapper;
    private final RequestStudentDTOMapper requestStudentDTOMapper;


    public StudentService(StudentRepository studentRepository, ResponseStudentDTOMapper responseStudentDTOMapper,
                          RequestStudentDTOMapper requestStudentDTOMapper) {
        this.studentRepository = studentRepository;
        this.responseStudentDTOMapper = responseStudentDTOMapper;
        this.requestStudentDTOMapper = requestStudentDTOMapper;
    }

    public List<ResponseStudentDTO> getStudents() {
        return studentRepository.findAll()
                .stream()
                .map(responseStudentDTOMapper)
                .toList();
    }

    public ResponseStudentDTO addNewStudent(RequestStudentDTO requestStudentDTO) {
        Student student = requestStudentDTOMapper.apply(requestStudentDTO);
        studentRepository.save(student);
        return responseStudentDTOMapper.apply(student);
    }

    public ResponseStudentDTO deleteStudent(long studentId) {
        //Vi returnerer det slettede person til frontend, for at at vise, hvad der er blevet slettet
        Student studentInDb = studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));
        ResponseStudentDTO responseStudentDTO = responseStudentDTOMapper.apply(studentInDb);


        for (Course course : studentInDb.getCourses()) {
            course.removeStudent(studentInDb);
        }

        studentInDb.setHouse(null);
        studentRepository.delete(studentInDb);

        return responseStudentDTO;
    }

    @Transactional
    public ResponseStudentDTO updateStudent(long id, RequestStudentDTO updatedStudent) {
        studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student with id not found"));
        Student s = requestStudentDTOMapper.apply(updatedStudent);

        //Vi bruger dens setter til at opdatere
        //HUSK @Transactional, så det er en transaction
        s.setId(id);

        //Gem ændringer i db
        studentRepository.save(s);
        return responseStudentDTOMapper.apply(s);
    }

    public ResponseStudentDTO getSingleStudent(long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student with id not found"));

        return responseStudentDTOMapper.apply(student);
    }

    public ResponseStudentDTO updateStudentPrefect(StudentDTOPrefect studentDTOPrefect, long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student with id not found"));

        student.setPrefect(studentDTOPrefect.prefect());

        //HUSK AT GEMME EFTER PATCH
        studentRepository.save(student);

        return responseStudentDTOMapper.apply(student);
    }

    public ResponseStudentDTO updateStudentSchoolYear(StudentDTOSchoolYear studentDTOSchoolYear, long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student with id not found"));

        student.setSchoolYear(studentDTOSchoolYear.schoolYear());

        //HUSK AT GEMME EFTER PATCH
        studentRepository.save(student);

        return responseStudentDTOMapper.apply(student);
    }

    public ResponseStudentDTO updateStudentGraduationYear(StudentDTOGraduationYear studentDTOGraduationYear, long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student with id not found"));

        student.setGraduationYear(studentDTOGraduationYear.graduationYear());

        //HUSK AT GEMME EFTER PATCH
        studentRepository.save(student);

        return responseStudentDTOMapper.apply(student);
    }
}
