package edu.hogwarts.springhogwarts.repositories;


import edu.hogwarts.springhogwarts.models.Student;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findFirstByFirstNameContainingOrLastNameContaining(String firstName, String lastName);
}
