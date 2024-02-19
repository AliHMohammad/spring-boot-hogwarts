package edu.hogwarts.springhogwarts.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class House {

    @Id
    private String name;
    private String founder;

    @JsonIgnore
    @ManyToMany(mappedBy = "houses")
    private Set<Color> colors = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "house")
    private Set<Student> students = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "house")
    private Set<Teacher> teachers = new HashSet<>();




    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFounder() {
        return founder;
    }

    public void setFounder(String founder) {
        this.founder = founder;
    }

    public Set<Color> getColors() {
        return colors;
    }

    public void addColor(Color color) {
        this.colors.add(color);
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void addStudent(Student student) {
        this.students.add(student);
    }

    public void setColors(Set<Color> colors) {
        this.colors = colors;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    public Set<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(Set<Teacher> teachers) {
        this.teachers = teachers;
    }

    public void removeTeacher(Teacher teacher) {
        this.teachers.remove(teacher);
        teacher.setHouse(null);
    }

    public void removeStudent(Student student) {
        this.students.remove(student);
        student.setHouse(null);
    }
}
