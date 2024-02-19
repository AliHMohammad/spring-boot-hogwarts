package edu.hogwarts.springhogwarts.services;

import edu.hogwarts.springhogwarts.dto.student.StudentDTO;
import edu.hogwarts.springhogwarts.dto.student.StudentDTOMapper;
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
    private final StudentDTOMapper studentDTOMapper;


    public StudentService(StudentRepository studentRepository, StudentDTOMapper studentDTOMapper) {
        this.studentRepository = studentRepository;
        this.studentDTOMapper = studentDTOMapper;
    }

    public List<StudentDTO> getStudents() {
        return studentRepository.findAll()
                .stream()
                .map(studentDTOMapper)
                .toList();
    }

    public StudentDTO addNewStudent(Student student) {
        studentRepository.save(student);
        return studentDTOMapper.apply(student);
    }

    public Student deleteStudent(long studentId) {
        //Vi returnerer det slettede person til frontend, for at at vise, hvad der er blevet slettet
        Student studentInDb = studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));


        for (Course course : studentInDb.getCourses()) {
            course.removeStudent(studentInDb);
        }

        studentInDb.setHouse(null);
        studentRepository.delete(studentInDb);

        //return studentDTOMapper.apply(studentInDb);
        return studentInDb;
    }

    @Transactional
    public StudentDTO updateStudent(long id, Student updatedStudent) {
        Student studentInDb = studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student with id not found"));


        //Vi bruger dens setter til at opdatere
        //Ændringen gemmer i db'en
        //HUSK @Transactional på metoden for at det virker
        studentInDb.setFullName(updatedStudent.getFullName());
        studentInDb.setDateOfBirth(updatedStudent.getDateOfBirth());
        studentInDb.setEnrollmentYear(updatedStudent.getEnrollmentYear());
        studentInDb.setGraduationYear(updatedStudent.getGraduationYear());
        studentInDb.setGraduated(updatedStudent.isGraduated());
        studentInDb.setPrefect(updatedStudent.isPrefect());
        studentInDb.setCourses(updatedStudent.getCourses());

        return studentDTOMapper.apply(studentInDb);
    }

    public StudentDTO getSingleStudent(long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student with id not found"));

        return studentDTOMapper.apply(student);
    }
}
