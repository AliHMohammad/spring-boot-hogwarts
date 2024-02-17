package edu.hogwarts.springhogwarts.services;

import edu.hogwarts.springhogwarts.models.Course;
import edu.hogwarts.springhogwarts.models.Student;
import edu.hogwarts.springhogwarts.repositories.CourseRepository;
import edu.hogwarts.springhogwarts.repositories.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
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
        studentRepository.save(student);
        return student;
    }

    public Student deleteStudent(long studentId) {
        //Vi returnerer det slettede person til frontend, for at at vise, hvad der er blevet slettet
        Student studentInDb = studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));


        for (Course course : studentInDb.getCourses()) {
            studentInDb.removeCourse(course);
        }

        studentInDb.setHouse(null);
        studentRepository.delete(studentInDb);

        return studentInDb;
    }

    @Transactional
    public Optional<Student> updateStudent(long id, Student updatedStudent) {
        Optional<Student> studentInDb = studentRepository.findById(id);

        if (studentInDb.isEmpty()) {
            return studentInDb;
        }

        //Vi bruger dens setter til at opdatere
        //Ændringen gemmer i db'en
        //HUSK @Transactional på metoden for at det virker
        studentInDb.get().setFullName(updatedStudent.getFullName());
        studentInDb.get().setDateOfBirth(updatedStudent.getDateOfBirth());
        studentInDb.get().setEnrollmentYear(updatedStudent.getEnrollmentYear());
        studentInDb.get().setGraduationYear(updatedStudent.getGraduationYear());
        studentInDb.get().setGraduated(updatedStudent.isGraduated());
        studentInDb.get().setPrefect(updatedStudent.isPrefect());
        studentInDb.get().setCourses(updatedStudent.getCourses());

        return studentInDb;
    }

    public Optional<Student> getSingleStudent(long id) {
        return studentRepository.findById(id);

    }
}
