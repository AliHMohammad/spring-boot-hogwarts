package edu.hogwarts.springhogwarts.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Student {

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

    @Min(value = 1, message = "Minimum schoolYear is 1")
    @Max(value = 7, message = "Maximum schoolYear is 7")
    private int schoolYear;

    @NotNull(message = "prefect must not be null")
    private boolean prefect;
    private int enrollmentYear;
    private Integer graduationYear;
    private boolean graduated;


    @JsonIgnore
    @ManyToMany(mappedBy = "students")
    private Set<Course> courses = new HashSet<>();

    @ManyToOne(
            cascade = CascadeType.REMOVE
    )
    @JoinColumn(
            name = "house",
            referencedColumnName = "name"
    )
    private House house;


    //Spring-boot ved åbenbart automatisk hvordan man assigner navnene vha. setFullName helt selvstændigt.
    //Defor ingen behov for nedenstående.
    /*public Student(String fullName, LocalDate dateOfBirth, boolean prefect, int enrollmentYear, int graduationYear,
                   boolean graduated) {
        setFullName(fullName);
        this.dateOfBirth = dateOfBirth;
        this.prefect = prefect;
        this.enrollmentYear = enrollmentYear;
        this.graduationYear = graduationYear;
        this.graduated = graduated;
    }*/

    public Student() {
    }

    public Student(String fullName) {
        this(fullName, null, false, 0, 0, false);
    }

    public Student(String fullName, LocalDate dateOfBirth, boolean prefect, int enrollmentYear, Integer graduationYear,
                   boolean graduated) {
        setFullName(fullName);
        this.dateOfBirth = dateOfBirth;
        this.prefect = prefect;
        this.enrollmentYear = enrollmentYear;
        this.graduationYear = graduationYear;
        this.graduated = graduated;
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


    public boolean isPrefect() {
        return prefect;
    }

    public void setPrefect(boolean prefect) {
        this.prefect = prefect;
    }

    public int getEnrollmentYear() {
        return enrollmentYear;
    }

    public void setEnrollmentYear(int enrollmentYear) {
        this.enrollmentYear = enrollmentYear;
    }

    public Integer getGraduationYear() {
        return graduationYear;
    }

    public void setGraduationYear(Integer graduationYear) {
        this.graduationYear = graduationYear;
        setGraduated(this.graduationYear != null);
    }

    public boolean isGraduated() {
        return graduated;
    }

    public void setGraduated(boolean graduated) {
        this.graduated = graduated;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void removeCourse(Course course) {
        this.courses.remove(course);
    }

    public void addCourse(Course course) {
        course.getStudents().add(this);
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
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

    public int getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(int schoolYear) {
        this.schoolYear = schoolYear;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", prefect=" + prefect +
                ", enrollmentYear=" + enrollmentYear +
                ", graduationYear=" + graduationYear +
                ", graduated=" + graduated +
                ", courses=" + courses +
                ", house=" + house +
                '}';
    }


}
