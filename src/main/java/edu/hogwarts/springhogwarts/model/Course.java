package edu.hogwarts.springhogwarts.model;

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

    @ManyToOne(
            cascade = CascadeType.ALL
    )
    @JoinColumn(
            name = "teacher_id",
            referencedColumnName = "id"
    )
    private Teacher teacher;

    @ManyToMany(
            cascade = CascadeType.ALL
    )
    @JoinTable(
            name = "students_courses",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private Set<Student> students = new HashSet<>();



    public Course() {

    }

    public Course(Long id, String subject, int schoolyear, boolean current) {
        this.id = id;
        this.subject = subject;
        this.schoolyear = schoolyear;
        this.current = current;
    }

    public Course(String subject, int schoolyear, boolean current) {
        this.subject = subject;
        this.schoolyear = schoolyear;
        this.current = current;
    }

    public Course(Course other) {
        this.subject = other.subject;
        this.schoolyear = other.schoolyear;
        this.current = other.current;
    }

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
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", subject='" + subject + '\'' +
                ", schoolyear=" + schoolyear +
                ", current=" + current +
                ", teacher=" + teacher +
                ", students=" + students +
                '}';
    }
}
