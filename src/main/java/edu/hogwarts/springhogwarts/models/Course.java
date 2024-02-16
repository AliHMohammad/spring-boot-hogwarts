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
        if (this.students.contains(student)) throw new IllegalStateException("Student is already assigned this course");

        this.students.add(student);
    }

    public void removeStudent(Student student) {
        if (!this.students.contains(student)) throw new IllegalStateException("Student is not enrolled this course");

        this.students.remove(student);
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public void removeTeacher(Teacher teacher) {
        if (this.teacher == null || !this.teacher.equals(teacher)) {
            throw new IllegalStateException("Teacher was not assigned this course");
        }

        setTeacher(null);
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", subject='" + subject + '\'' +
                ", teacher=" + teacher +
                ", students=" + students +
                '}';
    }


}
