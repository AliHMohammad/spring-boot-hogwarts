package edu.hogwarts.springhogwarts.repository;

import edu.hogwarts.springhogwarts.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
}
