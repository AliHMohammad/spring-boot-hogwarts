package edu.hogwarts.springhogwarts.repositories;

import edu.hogwarts.springhogwarts.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
}
