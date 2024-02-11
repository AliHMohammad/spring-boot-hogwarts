package edu.hogwarts.springhogwarts.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private LocalDate dateOfBirth;
    private boolean headOfHouse;
    private EmploymentType employment;
    private LocalDate employmentStart;
    private LocalDate employmentEnd;

    @Transient
    private int age;

    @JsonIgnore
    @OneToMany(
            mappedBy = "teacher"
    )
    private Set<Course> courses = new HashSet<>();




    //TODO Mangler house

    //TODO mangler setFullName() og getFullName(), Teacher
}
