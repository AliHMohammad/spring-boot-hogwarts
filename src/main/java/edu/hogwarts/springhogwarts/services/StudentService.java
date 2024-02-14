package edu.hogwarts.springhogwarts.services;

import edu.hogwarts.springhogwarts.models.Course;
import edu.hogwarts.springhogwarts.models.Student;
import edu.hogwarts.springhogwarts.repositories.CourseRepository;
import edu.hogwarts.springhogwarts.repositories.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public StudentService(StudentRepository studentRepository, CourseRepository courseRepository) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    public Student addNewStudent(Student student) {

        /*for (Course course : student.getCourses()){
            course.assignStudent(student);
        }*/

        studentRepository.save(student);

        return student;
    }

    public Optional<Student> deleteStudent(long studentId) {
        //Vi returnerer det slettede person til frontend, for at at vise, hvad der er blevet slettet
        Optional<Student> studentInDb = studentRepository.findById(studentId);

        if (studentInDb.isEmpty()) {
            return studentInDb;
        }

        for (Course course : studentInDb.get().getCourses()) {
            studentInDb.get().removeCourse(course);
        }

        studentInDb.get().setHouse(null);
        studentRepository.delete(studentInDb.get());
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
