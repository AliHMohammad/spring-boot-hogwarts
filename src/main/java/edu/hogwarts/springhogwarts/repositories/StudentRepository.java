package edu.hogwarts.springhogwarts.repositories;


import edu.hogwarts.springhogwarts.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
}
