package edu.hogwarts.springhogwarts.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.Period;
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

    @JsonIgnore
    @OneToMany(
            mappedBy = "teacher"
    )
    private Set<Course> courses = new HashSet<>();

    @ManyToOne(
            cascade = CascadeType.ALL
    )
    @JoinColumn(
            name = "house_id",
            referencedColumnName = "id"
    )
    private House house;

    public Teacher(Long id, String fullName, LocalDate dateOfBirth, boolean headOfHouse, EmploymentType employment,
                   LocalDate employmentStart, LocalDate employmentEnd) {
        this.id = id;
        setFullName(fullName);
        this.dateOfBirth = dateOfBirth;
        this.headOfHouse = headOfHouse;
        this.employment = employment;
        this.employmentStart = employmentStart;
        this.employmentEnd = employmentEnd;
    }

    public Teacher(String fullName, LocalDate dateOfBirth, boolean headOfHouse, EmploymentType employment,
                   LocalDate employmentStart, LocalDate employmentEnd) {
        setFullName(fullName);
        this.dateOfBirth = dateOfBirth;
        this.headOfHouse = headOfHouse;
        this.employment = employment;
        this.employmentStart = employmentStart;
        this.employmentEnd = employmentEnd;
    }

    public Teacher(String fullName, String dateOfBirthString, boolean headOfHouse, EmploymentType employment,
                   LocalDate employmentStart, LocalDate employmentEnd) {
        setFullName(fullName);
        setDateOfBirth(dateOfBirthString);
        this.headOfHouse = headOfHouse;
        this.employment = employment;
        this.employmentStart = employmentStart;
        this.employmentEnd = employmentEnd;
    }
    //TODO lav ovenstående constructor om så empStart og empEnd er String. Lav tilsvarende Setters.

    public Teacher() {
    }

    public Teacher(Teacher other) {
        this.firstName = other.firstName;
        this.middleName = other.middleName;
        this.lastName = other.lastName;
        this.dateOfBirth = other.dateOfBirth;
        this.headOfHouse = other.headOfHouse;
        this.employment = other.employment;
        this.employmentStart = other.employmentStart;
        this.employmentEnd = other.employmentEnd;
    }

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

    public void setDateOfBirth(String dateOfBirthString) {
        String[] dateArr = dateOfBirthString.split("-");
        this.dateOfBirth = LocalDate.of(Integer.parseInt(dateArr[2]), Integer.parseInt(dateArr[1]), Integer.parseInt(dateArr[0]));
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

    public int getAge() {
        return Period.between(this.dateOfBirth, LocalDate.now()).getYears();
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public House getHouse() {
        return house;
    }

    public void assignCourse(Course course) {
        this.courses.add(course);
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

    @Override
    public String toString() {
        return getFullName();
    }

}
