package edu.hogwarts.springhogwarts.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class House {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
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



    public House() {
    }

    public House(Long id, String name, String founder) {
        this.id = id;
        this.name = name;
        this.founder = founder;
    }

    public House(String name, String founder) {
        this.name = name;
        this.founder = founder;
    }

    public House(House other) {
        this.name = other.name;
        this.founder = other.founder;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    @Override
    public String toString() {
        return "House{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", founder='" + founder + '\'' +
                ", colors=" + colors +
                ", students=" + students +
                '}';
    }
}
