package edu.hogwarts.springhogwarts.models;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String subject;
    private int schoolyear;
    private boolean current;

    @ManyToOne()
    @JoinColumn(
            name = "teacher_id",
            referencedColumnName = "id"
    )
    private Teacher teacher;

    @ManyToMany()
    @JoinTable(
            name = "students_courses",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private Set<Student> students = new HashSet<>();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getSchoolyear() {
        return schoolyear;
    }

    public void setSchoolyear(int schoolyear) {
        this.schoolyear = schoolyear;
    }

    public boolean isCurrent() {
        return current;
    }

    public void setCurrent(boolean current) {
        this.current = current;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void assignStudent(Student student) {
        this.students.add(student);
        student.getCourses().add(this);
    }

    public void removeStudent(Student student) {
        this.students.remove(student);
        student.getCourses().remove(this);
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

}
