package edu.hogwarts.springhogwarts.repository;


import edu.hogwarts.springhogwarts.model.House;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HouseRepository extends JpaRepository<House, Long> {
}
