package edu.hogwarts.springhogwarts.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;
import java.time.Period;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Missing firstName")
    private String firstName;
    private String middleName;

    @NotBlank(message = "Missing lastName")
    private String lastName;

    @Past(message = "dateOfBirth must be in the past time")
    @NotNull(message = "dateOfBirth must not be null")
    private LocalDate dateOfBirth;
    private boolean headOfHouse;

    @Enumerated(EnumType.STRING)
    private EmploymentType employment;

    @PastOrPresent(message = "employmentStart must be past or present")
    private LocalDate employmentStart;
    private LocalDate employmentEnd;

    @JsonIgnore
    @OneToMany(
            mappedBy = "teacher"
    )
    private Set<Course> courses = new HashSet<>();

    @ManyToOne()
    @JoinColumn(
            name = "house_id",
            referencedColumnName = "id"
    )
    private House house;


    //Spring-boot ved åbenbart automatisk hvordan man assigner navnene vha. setFullName helt selvstændigt.
    //Defor ingen behov for nedenstående.
    /*public Teacher(String fullName, LocalDate dateOfBirth, boolean headOfHouse, EmploymentType employment,
                   LocalDate employmentStart, LocalDate employmentEnd) {
        setFullName(fullName);
        this.dateOfBirth = dateOfBirth;
        this.headOfHouse = headOfHouse;
        this.employment = employment;
        this.employmentStart = employmentStart;
        this.employmentEnd = employmentEnd;
    }*/

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public boolean isHeadOfHouse() {
        return headOfHouse;
    }

    public void setHeadOfHouse(boolean headOfHouse) {
        this.headOfHouse = headOfHouse;
    }

    public EmploymentType getEmployment() {
        return employment;
    }

    public void setEmployment(EmploymentType employment) {
        this.employment = employment;
    }

    public LocalDate getEmploymentStart() {
        return employmentStart;
    }

    public void setEmploymentStart(LocalDate employmentStart) {
        this.employmentStart = employmentStart;
    }

    public LocalDate getEmploymentEnd() {
        return employmentEnd;
    }

    public void setEmploymentEnd(LocalDate employmentEnd) {
        this.employmentEnd = employmentEnd;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
    }

    public String getFullName() {
        return hasMiddleName() ? this.firstName + " " + this.middleName + " " + this.lastName : this.firstName + " " + this.lastName;
    }

    public boolean hasMiddleName() {
        return this.middleName != null;
    }

    public void setFullName(String fullName) {
        int firstGap = fullName.indexOf(" ");
        int lastGap = fullName.lastIndexOf(" ");

        this.firstName = fullName.substring(0, firstGap);
        this.lastName = fullName.substring(lastGap+1);
        this.middleName = firstGap == lastGap ? null : fullName.substring(firstGap+1, lastGap);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void removeCourse(Course course) {
        this.courses.remove(course);
        course.setTeacher(null);
    }

    public void assignCourse(Course course) {
        course.setTeacher(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Teacher teacher = (Teacher) o;
        return Objects.equals(getFirstName(), teacher.getFirstName()) && Objects.equals(getMiddleName(),
                teacher.getMiddleName()) && Objects.equals(getLastName(), teacher.getLastName()) && Objects.equals(getDateOfBirth(),
                teacher.getDateOfBirth());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFirstName(), getMiddleName(), getLastName(), getDateOfBirth());
    }
}
