package edu.hogwarts.springhogwarts.service;

import edu.hogwarts.springhogwarts.model.Student;
import edu.hogwarts.springhogwarts.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    public Student addNewStudent(Student student) {
        Student studentToCreate = new Student(student);
        return studentRepository.save(studentToCreate);
    }

    public void deleteStudent(long studentId) {
        boolean exists = studentRepository.existsById(studentId);

        if (!exists) {
            throw new IllegalStateException("student with id: " + studentId + " does not exist in the db");
        }

        studentRepository.deleteById(studentId);
    }

    @Transactional
    public Student updateStudent(long id, Student updatedStudent) {
        Student studentInDb = studentRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException(
                        "Student with id " + id + " does not exist"
                ));

        //Vi bruger dens setter til at opdatere
        //Ændringen gemmer i db'en
        //HUSK @Transactional på metoden for at det virker
        studentInDb.setFullName(updatedStudent.getFullName());
        studentInDb.setDateOfBirth(updatedStudent.getDateOfBirth());
        studentInDb.setEnrollmentYear(updatedStudent.getEnrollmentYear());
        studentInDb.setGraduationYear(updatedStudent.getGraduationYear());
        studentInDb.setGraduated(updatedStudent.isGraduated());
        studentInDb.setPrefect(updatedStudent.isPrefect());

        return studentInDb;
    }

    public Student getSingleStudent(long id) {
        return studentRepository.findById(id)
                .orElseThrow(()-> new IllegalStateException(
                        "Student with id " + id + " does not exist"
                ));
    }
}
