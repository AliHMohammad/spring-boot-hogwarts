package edu.hogwarts.springhogwarts.repositories;

import edu.hogwarts.springhogwarts.models.Color;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColorRepository extends JpaRepository<Color, Long> {
}
